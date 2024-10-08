package com.ohgiraffers.visaproject.model.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApplyDTO {

    private Long applyNo;
    private String applicant;
    public String document;
    public String reason;
    public Date applyDate;

    private String fileName;

}
