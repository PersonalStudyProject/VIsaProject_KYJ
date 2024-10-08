package com.ohgiraffers.visaproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name="MemberManager")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="verification_code")  // 인증 코드 컬럼
    private String verificationCode;

    @Column(name="is_verified", nullable = false)  // 인증 여부를 저장할 컬럼
    private Boolean isVerified = false;  //기본값 설정

    @OneToMany(mappedBy = "memberManager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> reservations = new ArrayList<>(); // 회원의 예약 목록

    @Enumerated(value = EnumType.STRING)
    @Column(name = "MEMBER_ROLE")
    private Role role;
}
