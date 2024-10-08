package com.ohgiraffers.visaproject.model.dto;

import com.ohgiraffers.visaproject.model.entity.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberManagerDTO {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String verificationCode;
    private Boolean isVerified;
    //회원의 예약목록추가
    private List<ReservationDTO> reservations = new ArrayList<>();
    private Role role;
}
