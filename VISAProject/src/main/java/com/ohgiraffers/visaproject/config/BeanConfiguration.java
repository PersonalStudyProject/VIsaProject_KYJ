package com.ohgiraffers.visaproject.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {

        //modelmapper :객체간 매핑을 처리하는데 사용됨,주로 dto와 entity사이의 변환에 사용됨
        ModelMapper modelMapper = new ModelMapper();

        // ModelMapper의 매핑 전략 적용 - private 필드 매핑
        modelMapper.getConfiguration()

                //modelmapper가 필드에 접근할수있는 권한을 설정하는부분, private로 설정하면private까지매핑함
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)

                //필드명이 다른 객체간의 매핑을 허용하는 설정
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }
}
