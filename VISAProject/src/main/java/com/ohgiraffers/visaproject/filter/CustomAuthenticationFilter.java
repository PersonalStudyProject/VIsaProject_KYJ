package com.ohgiraffers.visaproject.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.visaproject.model.dto.MemberManagerDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;

//클라이언트가 로그인요청을 /login으로 json형식으로 보내면 attimptauthentication이 호출되어 요청응ㄹ 처리함
//getauthrequest를 통해json데이터를dto로 변환하고 사용자의 id,비번을 추출해서 usernamepasswordauthenticationtoken으로 반환함
//authenticationmanager가 이토큰을 받아 인증을 수행함, 인증에 성공하면설정된 authenticationsuccessandler가호출됨
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    //실제 인증 요청이 들어왔을때 실행되는 메서드, 로그인 요청을 처리하는역할을 함
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 로그인 정보를 추출해서 AuthenticationManger를 통해 로그인 정보를 넘겨준다.
        UsernamePasswordAuthenticationToken authRequest;

        try {
            //클라이언트로부터 요청된 사용자정보를 추출한후 setdetails로 해당 요청의 세부정보를 설정함
            authRequest = getAuthRequest(request);
            setDetails(request, authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //getAuthenticationManager로 인증시도후 실패시 예외를 던짐
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    //json형식의 데이터를 읽어들여 인증정보를 추출함
    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws IOException {

        //jackson라이브러리에서 제공하는 json데이터를 java객체로 변환해주는 클래스임
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE,true);
        //클라이언트로 받은 json데이터를 읽음 dto클래스로 요청데이터를 역직렬화함, 로그인 요청시 사용자의 아이디와 비밀번호를 담고있는 dto객체임
        MemberManagerDTO member = objectMapper.readValue(request.getInputStream(), MemberManagerDTO.class);

        //UsernamePasswordAuthenticationToken:사용자 아이디와 비밀번호 정보를 담고이음 스프링시큐리티의 표준인증 토큰임
        return new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
    }
}
