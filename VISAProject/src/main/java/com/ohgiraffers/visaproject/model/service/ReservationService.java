package com.ohgiraffers.visaproject.model.service;

import com.ohgiraffers.visaproject.model.dto.ApplyDTO;
import com.ohgiraffers.visaproject.model.dto.ReservationDTO;
import com.ohgiraffers.visaproject.model.entity.ApplyEntity;
import com.ohgiraffers.visaproject.model.entity.MemberManagerEntity;
import com.ohgiraffers.visaproject.model.entity.ReservationEntity;
import com.ohgiraffers.visaproject.model.repository.MemberManagerRepository;
import com.ohgiraffers.visaproject.model.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberManagerRepository memberManagerRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              MemberManagerRepository memberManagerRepository) {
        this.reservationRepository = reservationRepository;
        this.memberManagerRepository = memberManagerRepository;
    }

    @Transactional
    public ReservationEntity saveReservation(ReservationDTO reservationDTO) {

        ReservationEntity reservationEntity;

        //회원정보를 가져옴
        MemberManagerEntity member = memberManagerRepository.findById(reservationDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        if(reservationDTO.getReservationNo() !=null) {
            //예약번호로 기존 예약 정보 검색
            Optional<ReservationEntity> existingReservation = reservationRepository.findById(reservationDTO.getReservationNo());

            if (existingReservation.isPresent()) {

                //기존 예약정보가 있으면 업데이트
                reservationEntity = existingReservation.get();
                reservationEntity.setReservationName(reservationDTO.getReservationName());
                reservationEntity.setReservationDate(reservationDTO.getReservationDate());
                reservationEntity.setReservationTime(reservationDTO.getReservationTime());

                reservationEntity.setMemberManager(member);  //회원정보설정
            } else {
                //기존예약정보가 없으면 새로생성
                reservationEntity = convertToEntity(reservationDTO);
                //예약번호는 null로 두어야 자동생성됨
//                reservationEntity.setReservationNo(null); //예약번호를 null로설정
                reservationEntity.setMemberManager(member);
            }
        }else{
            //예약번호가 없으면 새로생성
            reservationEntity = convertToEntity(reservationDTO);
//            reservationEntity.setReservationNo(null);//자동생성
            reservationEntity.setMemberManager(member);
        }

        //저장후 반환
        return reservationRepository.save(reservationEntity);
//        ReservationEntity reservationEntity = convertToEntity(reservationDTO);
//        ReservationEntity savedEntity = reservationRepository.save(reservationEntity);
//        return savedEntity;
    }

    private ReservationEntity convertToEntity(ReservationDTO reservationDTO) {
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setReservationNo(reservationDTO.getReservationNo());
        reservationEntity.setReservationName(reservationDTO.getReservationName());
        reservationEntity.setReservationDate(reservationDTO.getReservationDate());
        reservationEntity.setReservationTime(reservationDTO.getReservationTime());
        return reservationEntity;

    }

    public List<ReservationDTO> getAllReservations(Long memberId) {
        List<ReservationEntity> reservations = reservationRepository.findByMemberManager_Id(memberId);

        return reservations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ReservationDTO convertToDTO(ReservationEntity reservationEntity){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationNo(reservationEntity.getReservationNo());
        reservationDTO.setReservationName(reservationEntity.getReservationName());
        reservationDTO.setReservationDate(reservationEntity.getReservationDate());
        reservationDTO.setReservationTime(reservationEntity.getReservationTime());

        return reservationDTO;
    }
}
