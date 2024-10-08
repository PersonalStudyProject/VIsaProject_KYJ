package com.ohgiraffers.visaproject.model.repository;

import com.ohgiraffers.visaproject.model.entity.MemberManagerEntity;
import com.ohgiraffers.visaproject.model.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    boolean existsByMemberManagerAndReservationDateAndReservationTime(MemberManagerEntity memberManager, LocalDate date, LocalTime time);

    // 회원별 예약조회 메서드
    List<ReservationEntity> findByMemberManager(MemberManagerEntity memberManager);
    List<ReservationEntity> findByMemberManager_Id(Long memberId); // 기존 메서드 이름 변경
}