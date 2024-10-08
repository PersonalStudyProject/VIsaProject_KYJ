package com.ohgiraffers.visaproject.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminLoginDTO {

    private Long id;
    private String username;
    private String password;
}
