package com.ohgiraffers.visaproject.model.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

@Service
@Slf4j
public class MailService {

        @Autowired
        private JavaMailSender sender;  // 필드에서 의존성 주입

        //메일 메시지를 생성하는 메서드
        private MimeMessage createMessage(String target, String code) throws MessagingException, UnsupportedEncodingException {
            try {
                MimeMessage message = sender.createMimeMessage();
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(target));
                message.setSubject("이메일 인증 코드입니다.");

                String detail = "";
                detail += "<div style='margin:100px;'>";
                detail += "<h1> 안녕하세요</h1>";
                detail += "<br>";
                detail += "<div align='center' style='border:1px solid black; font-family:verdana';>";
                detail += "<h3 style='color:blue;'>인증 코드입니다.</h3>";
                detail += "<div style='font-size:130%'>";
                detail += "CODE : <strong>";
                detail += code + "</strong><div><br/> "; // 메일에 인증번호 넣기
                detail += "</div>";
                detail += "</div>";
                message.setText(detail, "utf-8", "html");
                message.setFrom(new InternetAddress("yeonjinKn.n@gmail.com", "강연진"));  //발신자 설정

                return message;
            }catch (MessagingException e){
                e.printStackTrace();
                throw new RuntimeException("메일 메세지 생성중 오류발생:"+e.getMessage(),e);
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
                throw new RuntimeException("지원하지 않는 인코딩" + e.getMessage(), e);
            }
        }

    //인증코드를 생성하는 메서드
    private String createCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for(int i=0; i<8; i++){
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }

        //메일을 보내는 메서드
        public String sendMessage(String target){
            String code = createCode();
            try {
                MimeMessage message = createMessage(target, code);
                sender.send(message);
                log.info("인증 코드가 성공적으로 전송되었습니다: " + code);
            }catch (Exception e){
                log.error("메일 전송 오류 발생", e);
                //TODO : 추후 예외 통합 처리 할 때 여기서 오류 넘겨줘야 함
                throw  new RuntimeException("메일 전송 오류: " + e.getMessage(), e);
            }
            return code;
        }

        //이메일과 인증번호를 받아서 인증 메일을 전송하는 메서드
    public void sendVerificationEmail(String email) {
        String code = createCode();  //인증코드를 생성
        try {
            MimeMessage message = createMessage(email, code);  //이메일에 인증코드 전송
            sender.send(message);
            log.info("인증번호가 전송되었습니다: " + code);
        } catch (Exception e) {
            log.error("인증 메일 전송 오류 발생", e);
            throw new RuntimeException("인증 메일 전송 실패");
        }

    }
}

