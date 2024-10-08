package com.ohgiraffers.visaproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*swagger를 설정해서 스프링애플리케이션의 api문서를 자동으로 생성하는 설정을 포함함
 * 스웨거는 api의 명세를 자동으로 문서화하는데 유용한 도구*/
@OpenAPIDefinition(
        info = @Info(title = "JWT 사용 연습",  //문서의 제목을 설정함
                description = "JWT 사용 예제", //문서에 대한 설명을 추가함
                version = "v1"))  //api의 버전을 설정함
@Configuration
public class SwaggerConfig {

    @Bean
    //특정api 경로를 그룹화하는데 사용됨, 스웨거 문서에 포함될 api그룹을 정의함
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/api/v1/**", "/auth/**", "/test/**"};    // Swagger에서 처리 되었으면 하는 경로 설정

        //스웨거ui에서 보여줄 api그룹을 정의함
        return GroupedOpenApi.builder()
                .group("api-v1")  //api그룹이름
                .pathsToMatch(paths)  //문서화할 경로 패턴을 설정
                .build();
    }

}
