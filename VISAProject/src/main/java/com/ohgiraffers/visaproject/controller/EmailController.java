package com.ohgiraffers.visaproject.controller;

import com.ohgiraffers.visaproject.model.service.MailService;
import com.ohgiraffers.visaproject.model.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000") //프론트앤드 도메인설정
@RequestMapping("/")
public class EmailController {

    private final MailService mailService;
    private final Map<String, String> emailVerificationCodes = new HashMap<>(); // 이메일 인증번호 저장

    public EmailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestParam String email){
        log.info("email : "+email);
        String code = mailService.sendMessage(email);
        return new ResponseEntity<>(code,null, HttpStatus.OK);
    }

    //get요청으로 verification code를 전송
    @GetMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        log.info("Received email for verification: {}", email);

        // 기존 이메일에 대한 인증 코드 확인 후, 없으면 새로 생성
        String verificationCode = emailVerificationCodes.get(email);
        if (verificationCode == null) {
            verificationCode = mailService.sendMessage(email);  // 이메일로 인증 코드 발송
            emailVerificationCodes.put(email, verificationCode);  // 인증 코드를 저장
        }

        log.info("Verification code sent: {}", verificationCode);
        return ResponseEntity.ok("Verification code sent to: " + email);
    }

//이메일 인증번호 전송
@PostMapping("/send-verification-email")
public ResponseEntity<?> sendVerificationEmail(@RequestBody Map<String, String> request) {
    String email = request.get("email");
    log.info("Received email: {}", email);

    String code = mailService.sendMessage(email);  // 인증코드를 생성하고 전송
    emailVerificationCodes.put(email, code); // 인증코드를 맵에 저장
    log.info("Sent verification code: {}", code);

    return ResponseEntity.ok("이메일 인증번호가 전송되었습니다.");
}

    //이메일 인증번호 검증
    @PostMapping("/verify-email-code")
    public ResponseEntity<String> verifyEmailCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");

        log.info("Verifying code for email: {}", email);

        String savedCode = emailVerificationCodes.get(email);
        log.info("Expected code: {}, Provided code: {}", savedCode, code);

        if (savedCode != null && savedCode.equals(code)) {
            emailVerificationCodes.remove(email); // 인증번호 사용 후 삭제
            return ResponseEntity.ok("이메일 인증번호가 일치합니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 인증번호가 일치하지 않습니다.");
        }
    }

}
