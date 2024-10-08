package com.ohgiraffers.visaproject.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisticsDTO {

    private Long totalCount;  //총갯수
    private Long applyCount;               // 총 신청 수
    private Long memberCount;              // 총 회원 수
    private Long reservationCount;         // 총 예약 수
    private Double averageReservationPerMember; // 회원당 평균 예약 수
    private Double averageValue; //평균값


}
