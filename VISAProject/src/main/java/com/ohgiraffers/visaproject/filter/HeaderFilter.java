package com.ohgiraffers.visaproject.filter;

//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.LogRecord;

//cors정책을 설정하기위해 사용하는 서블릿 필터
//cors는 브라우저가 다른 도메인에서 리소스를 요청할떄 발생하는 보안문제를 해결함
//서버의 응답 헤더에 cors관련설정을 추가하여 클라이언트가 도메인에서 리소스를 요청할떄 허용하도록 설정함
//클라이언트가 다른 도메인에서 요청할떄 크로스 도메인 요청을 처리할수잇도록 응답에 cors관련헤더를 추가하여cors문제를 해결함
public class HeaderFilter implements Filter {

    @Override
    //요청이 들어오면 이 메서드가 호출되어 요청을 처리한뒤 필터체인의 다음 요소로 전달함
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //servletresponse객체를 httpservletresponse로 변환함, 이변환을 통해 응답에 헤더를 설정할수있음
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");  // 모든 출처에서의 요청을 허용
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");  //외부 요청에 허용할 메서드
        res.setHeader("Access-Control-Max-Age", "3600"); // 캐싱을 허용할 시간

        //클라이언트가 요청할떄 사용할수있는헤더를 정의함
        res.setHeader("Access-Control-Allow-Headers",
                "Access-Control-Allow-Origin, Access-Control-Allow-Headers, X-Requested-With, Content-Type, Authorization, X-XSRF-token"
        );

        //자격증명설정, 브라우저가 자격증명(쿠키나 인증정보)을 포함할수있는지 여부를 설정함
        res.setHeader("Access-Control-Allow-Credentials", "false");

        //필터체인의 다음요소로 요청을 전달하여 필터링이 끝나고 요청이 처리되도록함
        chain.doFilter(request,response);
    }
}
