package com.ohgiraffers.visaproject.filter;

import com.ohgiraffers.visaproject.model.service.CustomUserDetails;
import com.ohgiraffers.visaproject.common.AuthConstants;
import com.ohgiraffers.visaproject.model.entity.MemberManagerEntity;
import com.ohgiraffers.visaproject.model.entity.Role;
import com.ohgiraffers.visaproject.util.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.context.support.BeanDefinitionDsl;
import com.ohgiraffers.visaproject.util.TokenUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    //basicauthenticationfilter를 상속받아 기본인증필터로사용, 생성자에서authenticationmanager를 받아 상위클래세으ㅔ 전달
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    //요청이 필터를 거칠때마다 호출되며, 토큰을 확인하고 유효성을 검사하는 로직
    //특정경로(roleleesslist)는 인증이 필요하지않으므로 필터를 적용하지 않음
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //필터링을 생략할 경로, 스웨거 문서관련 경로들이 포함되있음(인증이 필요없으므로 필터적용안하는것)
        List<String> roleLeessList = Arrays.asList(
                "/auth/signup",
                "/swagger-ui/(.*)",
                "/swagger-ui/index.html",
                "/v3/api-docs",
                "/v3/api-docs/(.*)",
                "/swagger-resources",
                "/swagger-resources/(.*)"
        );

        //rolelesslist에 정의된패턴중,현재 요청url과 일치하는것이 있는지 확인함,url과 일치하는경로가 있으면 필터를 통과시킴
        if(roleLeessList.stream().anyMatch(uri -> roleLeessList.stream().anyMatch(pattern ->

                //요청url과 해당 패턴과 일치하는지 검사함
                Pattern.matches(pattern, request.getRequestURI())))){

            //경로가 일치하면 추가적인 인증절차를 생략하고 chain.dofilter를 호출하여 요철을 다음 필터로 넘김
            chain.doFilter(request,response);

            //return으로 더이상 필터 로직을 진행하지 않음
            return;
        }

        //jwt토큰확인, 요청헤더에서 jwt토큰을 추출(authconstants.auth_header)
        String header = request.getHeader(AuthConstants.AUTH_HEADER);


        // 유효한 토큰 확인
        try {

            if(header != null && !header.equalsIgnoreCase("")){

                //토큰이 비어있지 않으면 tokenuils.splitheader로 토큰을 파싱
                String token = TokenUtils.splitHeader(header);

                //유효한 토큰인지 확인
                if(TokenUtils.isValidToken(token)){

                    //토큰에서 사용자 정보 추출
                    Claims claims = TokenUtils.getClaimsFromToken(token);

                    // 토큰에 담긴 정보로 Member 객체를 만든다.
                    // (security context에 등록될 인증 객체(UserDetail)를 만들기 위해)
                    MemberManagerEntity member = MemberManagerEntity.builder()
                            .username(claims.get("memberName").toString())
                            .email(claims.get("memberEmail").toString())
                            .role(Role.valueOf(claims.get("memberRole").toString()))
                            .build();

                    //객체에 멤버 정보를 설정함
                    //customUserDetails는 사용자의 세무정보임member객체는jwt토큰을 통해 파싱된 사용자정보를 가지고있음
                    CustomUserDetails userDetails = new CustomUserDetails();
                    userDetails.setMember(member);

                    //useernamapasswordauthenticariontoken을 생성하고 인증토큰으로 사용함
                    AbstractAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    //요청(request)에서 추가적인 세부사항을 추출하여 인증 토큰에 설정함
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));

                    //시큐리티context에 인증정보를 저장하여 이후의 요청이 이 사용자가 인증되었음을 알수있도록함
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    // 다음 필터로 이동하세요
                    chain.doFilter(request, response);
                }else{
                    throw new RuntimeException("토큰이 유효하지 않습니다.");
                }
            }else{
                throw new RuntimeException("토큰이 존재하지 않습니다.");
            }

            //토큰 유효성 검사중 예외가 발생할경우 예외메세지를 json형태로 응답에 기록함
            //예외발생시,인코딩타입을 설정하고 printwriter를 통해 json형식의 응답을 클라이언트에게 반환함
            //jsonresponsewrapper메서드로 예외 정볼르 가공한후 클라이언트가 이해할수있는 방식으로 오류정보전달함
        }catch (Exception e){

            // Exception 발생시 Exception 내용을 응답해준다.
            response.setCharacterEncoding("UTF-8");

            //응답의 contenttype을 json으로 설정하며 클라이언트가 json형식의 데이터를 받을 것을 명시함
            response.setContentType("application/json");

            //응답본문을 작성하는데 사용함
            PrintWriter printWriter = response.getWriter();

            //예외를 json형식으로 변환함
            JSONObject jsonObject = jsonresponseWrapper(e);

            //json객체를 응답 본문에 출력함
            printWriter.print(jsonObject);

            //flush는 출력스트림을 강제로 비워서 남아있는 데이터를 즉시출력함
            printWriter.flush();

            //close해서 더이상 스트림을 출력하지 않도록함
            printWriter.close();
        }
    }

    //예외의 종류에따라 적절한 오류메세지를 설정함,예외정보를 json형태로 변환해 클라이언트에게 반환함
    private JSONObject jsonresponseWrapper(Exception e) {
        String resultMsg = "";

        if (e instanceof ExpiredJwtException) { //토큰이 만료되었음을 나타냄
            // 토큰 만료 exception
            resultMsg = "Token Expired";

        } else if (e instanceof SignatureException) { //토큰의 서명이 유효하지 않음을 나타냄

            // 토큰 서명 exception
            resultMsg = "TOKEN SignatureException Login";
        }

        // JWT 토큰내에서 오류 발생 시
        else if (e instanceof JwtException) {  //jwt파싱오류를 나타냄
            resultMsg = "TOKEN Parsing JwtException";
        }
        // 이외 JTW 토큰내에서 오류 발생
        else {
            System.out.println(e.getMessage());  //그외 나머지 오류일경우 반환할 메세지
            resultMsg = "OTHER TOKEN ERROR";
        }

        //json데이터생성
        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status", 401); //http상태코드로 401상태 나타냄
        jsonMap.put("message", resultMsg); //위에서 설정된 예외 메시지 resultmag
        jsonMap.put("reason", e.getMessage()); //실제 예외메세지를 담아 클라이언트에게 상세오류정보를 제공함

        //json객체로 변환
        JSONObject jsonObject = new JSONObject(jsonMap);
        return jsonObject;
    }

}
