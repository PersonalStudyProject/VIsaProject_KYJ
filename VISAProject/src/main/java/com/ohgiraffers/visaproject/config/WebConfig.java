package com.ohgiraffers.visaproject.config;

import com.ohgiraffers.visaproject.filter.HeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
//WebMvcConfigurer 스프링mvc설정을 커스터마이징할수있게 해주는 메서드를 제공함,addresourcehandler쓰려고한거임
public class WebConfig implements WebMvcConfigurer{

    //정적리소스 경로설정
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/static/", "classpath:/public/",
            "classpath:/", "classpath:/resources/", "classpath:/META-INF/resources/",
            "classpath:/META-INF/resources/webjars/"};

    //정적리소스 핸들러설정
    //요청된url패턴에 정적리소스를 제공하는 설정,/로 시작하는 모든 요청에대해 지정된 리소스 위칭서 정적리소스찾음
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //모든 요청을 해당 리소스경로에서 찾음
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    //스프링필터기능을 이용해http요청 및 응답의 헤더를 처리하는 역할을함
    @Bean
    public HeaderFilter createHeaderFilter() {
        return new HeaderFilter();
    }

}
