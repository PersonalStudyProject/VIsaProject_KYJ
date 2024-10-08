package com.ohgiraffers.visaproject.model.service;

import com.ohgiraffers.visaproject.model.dto.AdminLoginDTO;
import com.ohgiraffers.visaproject.model.dto.MemberManagerDTO;
import com.ohgiraffers.visaproject.model.dto.ReservationDTO;
import com.ohgiraffers.visaproject.model.entity.MemberManagerEntity;
import com.ohgiraffers.visaproject.model.entity.ReservationEntity;
import com.ohgiraffers.visaproject.model.repository.MemberManagerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberManagerService {

    private final MemberManagerRepository memberManagerRepository;
    private final MailService mailService;  //불변성 확보위해 final로 설정
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberManagerService(MemberManagerRepository memberManagerRepository, MailService mailService,PasswordEncoder passwordEncoder) {
        this.memberManagerRepository = memberManagerRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberManagerEntity saveMember(MemberManagerDTO memberDTO) {

        if(memberDTO.getEmail() == null || !memberDTO.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            throw new IllegalArgumentException("유효하지 않은 이메일 주소입니다");
        }

        log.info("이메일:{}", memberDTO.getEmail());  //이메일 확인

        String verificationCode = mailService.sendMessage(memberDTO.getEmail());

        // 비밀번호 해싱
        String hashedPassword = passwordEncoder.encode(memberDTO.getPassword());

        //예약목록처리
        List<ReservationEntity> reservations = memberDTO.getReservations() != null ?
                memberDTO.getReservations().stream()
                        .map(this::convertToEntity)
                        .collect(Collectors.toList()) : new ArrayList<>();

        MemberManagerEntity member = MemberManagerEntity.builder()
                .id(memberDTO.getId())
                .username(memberDTO.getUsername())
                .password(hashedPassword)
                .email(memberDTO.getEmail())
                .verificationCode(verificationCode) // 인증 코드 저장
                .isVerified(false)
                .reservations(reservations) //예약목록추가
                .build();
        return memberManagerRepository.save(member);
    }

    private ReservationEntity convertToEntity(ReservationDTO reservationDTO){
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setReservationNo(reservationDTO.getReservationNo());
        reservationEntity.setReservationName(reservationDTO.getReservationName());
        reservationEntity.setReservationDate(reservationDTO.getReservationDate());
        reservationEntity.setReservationTime(reservationDTO.getReservationTime());
        return reservationEntity;
    }

    public List<MemberManagerDTO> getAllMembers() {
        List<MemberManagerEntity> members = memberManagerRepository.findAll();

        return members.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MemberManagerDTO convertToDTO(MemberManagerEntity memberManagerEntity){
        MemberManagerDTO memberManagerDTO = new MemberManagerDTO();
        memberManagerDTO.setId(memberManagerEntity.getId());
        memberManagerDTO.setUsername(memberManagerEntity.getUsername());
        memberManagerDTO.setPassword(memberManagerEntity.getPassword());
        memberManagerDTO.setEmail(memberManagerEntity.getEmail());

        //예약목록 dto에 추가
        List<ReservationDTO> reservationDTOS = memberManagerEntity.getReservations().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        memberManagerDTO.setReservations(reservationDTOS);

        return memberManagerDTO;
    }

    private ReservationDTO convertToDTO(ReservationEntity reservationEntity) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationNo(reservationEntity.getReservationNo());
        reservationDTO.setReservationName(reservationEntity.getReservationName());
        reservationDTO.setReservationDate(reservationEntity.getReservationDate());
        reservationDTO.setReservationTime(reservationEntity.getReservationTime());
        return reservationDTO;
    }

    public boolean authenticateAdmin(AdminLoginDTO adminLoginDTO) {
        Optional<MemberManagerEntity> memberOptional = memberManagerRepository.findByUsername(adminLoginDTO.getUsername());

        if (memberOptional.isPresent()) {
            MemberManagerEntity member = memberOptional.get();
            return passwordEncoder.matches(adminLoginDTO.getPassword(), member.getPassword());
        }

        return false;
    }


}
