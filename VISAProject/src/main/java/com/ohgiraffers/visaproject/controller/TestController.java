package com.ohgiraffers.visaproject.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//특정권한을 가진 사용자만 특정엔드포인트에 접근할수 있도록 제어하는 api컨트롤러
//특정권한을 요구하며 해당 권한을 가진 사용자만 접근가능함
@RestController  //rest api의 엔드포인트를 처리하는 컨트롤러임
@RequestMapping("/api/v1/test")
public class TestController {

    // USER일때 사용가능함
    @PreAuthorize("hasAuthority('USER')") //user권한사용자만 접근가능하고 호출이성공하면 메세지 반환함
    @GetMapping("/user")
    public String testUser() {
        return "user 권한만 접근 가능한 test success";
    }

    // ADMIN일때 사용가능함
    @PreAuthorize("hasAuthority('ADMIN')") //해당메서드에 접근하기전에 사용자에게 admin권한있는지 확인함
    @GetMapping("/admin")
    public String testAdmin() {
        return "Admin 권한만 접근 가능한 test success";
    }

}
