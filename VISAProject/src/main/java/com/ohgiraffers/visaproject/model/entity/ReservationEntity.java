package com.ohgiraffers.visaproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Table(name="reservation")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_no")
    private Long reservationNo;

    @Column(name="reservation_name")
    private String reservationName;

    @Column(name="reservation_date")
    private LocalDate reservationDate;

    @Column(name="reservation_time")
    private LocalTime reservationTime;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false) // 회원을 참조하는 외래 키
    private MemberManagerEntity memberManager; // 해당 예약을 한 회원

}
