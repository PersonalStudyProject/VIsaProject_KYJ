package com.ohgiraffers.visaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.ohgiraffers.visaproject.model.entity")
public class VisaProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisaProjectApplication.class, args);
    }

}
