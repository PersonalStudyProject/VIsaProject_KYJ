package com.ohgiraffers.visaproject.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Long totalCount;            //총 합계
    private Long applyCount;               // 총 신청 수
    private Long memberCount;              // 총 회원 수
    private Long reservationCount;         // 총 예약 수
    private Double averageReservationPerMember; // 회원당 평균 예약 수
    private Double averageValue;


}
