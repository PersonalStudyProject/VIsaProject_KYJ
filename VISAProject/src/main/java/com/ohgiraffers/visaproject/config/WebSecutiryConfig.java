package com.ohgiraffers.visaproject.config;

import com.ohgiraffers.visaproject.filter.CustomAuthenticationFilter;
import com.ohgiraffers.visaproject.filter.JwtAuthorizationFilter;
import com.ohgiraffers.visaproject.handler.CustomAuthFailUserHandler;
import com.ohgiraffers.visaproject.handler.CustomAuthSuccessHandler;
import com.ohgiraffers.visaproject.handler.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableMethodSecurity //메소드 수준에서의 보안활성화
@EnableWebSecurity //웹보안기능 활성화
public class WebSecutiryConfig {

    @Bean
    //정적 리소스에 대한 요청을 보안필터에서 제외시킴, css, js이미지같은 파일은 인증을 거치지않도록 설정
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    //보안 필터체인을 구성하는 핵심 메서드, httpsecurity객체를 통해 보안 설정을 커스터마이징함
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)  //cref비활성화,jwt기반인증에서는 세션사용하지 않아서 보호안함
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션비활성화(jwt를 통해 상태관리)
                .formLogin(AbstractHttpConfigurer::disable) //폼로그인 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) //기본인증 비활성화,jwt사용하므로
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) //커스텀 인증 필터 추가
                .addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class) //jwt인증 필터추가 ,헤더에 토큰이 있을경우 인가해줌

                .authorizeHttpRequests(auth -> auth
                        //특정url은 인증 불필요
                        .requestMatchers("/auth/signup", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll() // Swagger 관련 리소스와 회원가입 경로 허용
                        .anyRequest()
                        .authenticated() //나머지 요청은 인증 필요
                );

        return http.build();
    }


    @Bean
    //커스텀 인증 공급자(customAuthenticationProvider)를 설정함
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(customAuthenticationProvider());
    }

    @Bean
    //커스텀인증로직을 구현, 사용자정보를 확인하고 인증 여부를 결정함
    public CustomAuthenticationProvider customAuthenticationProvider(){
        return new CustomAuthenticationProvider();
    }

    @Bean
    //비밀번호 암호화하기 위한 인코더
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    //인증 요청을 처리하는 커스텀필터, /login경로에서 처리함, 성공/실패 핸들러도 설정
    public CustomAuthenticationFilter customAuthenticationFilter(){

        //customauthenticationfilter객체생성하면서authenticationmanager를 주입함, 인증요청시 사용자 정보를 검증함
        //authenticationManagersms 는 providermanager를 사용해 인증공급자(customauthenticationprovider)와 연결됨
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());

        //필터가 인증요청을 처리할 경로를 지정함,클라이언트가 /login으로 post요청을 보낼때 필터가 동작하여 인증을 처리함
        customAuthenticationFilter.setFilterProcessesUrl("/login");

        //인증에 성공했을떄 실행되는 핸들러, 인증성공후 사용자를 어떻게 처리할지 결정하는역할 토큰을 발급하거나 특정페이지로 리다이렉트 할수있음
        customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthLoginSuccessHandler());

        // 인증 실패시 동작할 핸들러 설정
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthFailUserHandler());

        //필터설정이 끝난후 필요한 속성들이 제대로 설정되었는지 검증하는 역할을 함
        customAuthenticationFilter.afterPropertiesSet();

        // 완성된 CustomAuthenticationFilter 를 반환한다.
        return customAuthenticationFilter;
    }

    @Bean
    public CustomAuthSuccessHandler customAuthLoginSuccessHandler(){
        return new CustomAuthSuccessHandler();
    }

    @Bean
    public CustomAuthFailUserHandler customAuthFailUserHandler(){
        return new CustomAuthFailUserHandler();
    }

    @Bean
    //jwt기반으로 요청의 권한을 확인하는 필터, 요청에 포함된jwt토큰을 분석하여 사용자 정보를 추출하고 권한검증
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter(authenticationManager());
    }


}
