package com.ohgiraffers.visaproject.controller;

import com.ohgiraffers.visaproject.common.ResponseMessage;
import com.ohgiraffers.visaproject.model.dto.MemberManagerDTO;
import com.ohgiraffers.visaproject.model.entity.MemberManagerEntity;
import com.ohgiraffers.visaproject.model.service.MemberManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000") //프론트앤드 도메인설정
@RequestMapping("/")
public class MemberManagerController {

    private final MemberManagerService memberManagerService;

    @Autowired
    public MemberManagerController(MemberManagerService memberManagerService) {
        this.memberManagerService = memberManagerService;
    }

    @GetMapping("/members")
    public ResponseEntity<ResponseMessage> findAllMembers() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 모든 회원 정보를 가져온다
        List<MemberManagerDTO> members = memberManagerService.getAllMembers();

        // 응답에 포함할 데이터를 준비
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("members", members);

        // 응답 메시지 생성
        ResponseMessage responseMessage = new ResponseMessage(
                200,
                "조회 성공!",
                responseMap);

        // ResponseEntity 반환
        return ResponseEntity.ok().headers(headers).body(responseMessage);
    }

    @PostMapping("/members")
    public ResponseEntity<MemberManagerEntity> createMember(@RequestBody MemberManagerDTO memberDTO) {
        MemberManagerEntity member = memberManagerService.saveMember(memberDTO);
        return ResponseEntity.ok(member);
    }

    @PostMapping("/api/members")
    public ResponseEntity<String> saveMember(@RequestBody MemberManagerDTO memberManagerDTO) {
        memberManagerService.saveMember(memberManagerDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

//        return ResponseEntity.ok("회원 정보가 성공적으로 저장되었습니다.");
        return ResponseEntity.ok().headers(headers).body("회원 정보가 성공적으로 저장되었습니다.");
    }
}


