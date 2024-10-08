package com.ohgiraffers.visaproject.controller;

import com.ohgiraffers.visaproject.common.ResponseMessage;
import com.ohgiraffers.visaproject.model.dto.AdminLoginDTO;
import com.ohgiraffers.visaproject.model.dto.MemberManagerDTO;
import com.ohgiraffers.visaproject.model.dto.ReservationDTO;
import com.ohgiraffers.visaproject.model.entity.MemberManagerEntity;
import com.ohgiraffers.visaproject.model.entity.ReservationEntity;
import com.ohgiraffers.visaproject.model.repository.MemberManagerRepository;
import com.ohgiraffers.visaproject.model.repository.ReservationRepository;
import com.ohgiraffers.visaproject.model.service.MemberManagerService;
import com.ohgiraffers.visaproject.model.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5000") //프론트앤드 도메인설정
@RequestMapping("/")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    private final MemberManagerRepository memberManagerRepository;
    private final MemberManagerService memberManagerService;

    @Autowired
    public ReservationController(ReservationService reservationService, ReservationRepository reservationRepository, MemberManagerRepository memberManagerRepository, MemberManagerService memberManagerService) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
        this.memberManagerRepository = memberManagerRepository;
        this.memberManagerService = memberManagerService;
    }

    @GetMapping("/Reservation")
    public ResponseEntity<ResponseMessage> findAllReservations(@RequestParam Long memberId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<ReservationDTO> reservations = reservationService.getAllReservations(memberId);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("reservations", reservations);

        ResponseMessage responseMessage = new ResponseMessage(
                200,
                "조회성공!",
                responseMap
        );

        return ResponseEntity.ok().headers(headers).body(responseMessage);
    }

    @PostMapping("/Reservation")
    public ResponseEntity<ReservationEntity> createReservation(@RequestBody ReservationDTO reservationDTO) {
        log.info("Received reservation: {}", reservationDTO);
        ReservationEntity reservation = reservationService.saveReservation(reservationDTO);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/api/Reservation")
    @Transactional
    public ResponseEntity<String> saveReservation(@RequestBody ReservationDTO reservationDTO) {
        log.info("Received reservation: {}", reservationDTO);

        // 회원 정보 조회
        MemberManagerEntity member = memberManagerRepository.findById(reservationDTO.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("회원이 존재하지 않습니다."));

        //중복예약 확인
        boolean exists = reservationRepository.existsByMemberManagerAndReservationDateAndReservationTime(member, reservationDTO.getReservationDate(), reservationDTO.getReservationTime());
        if(exists){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 예약이 존재합니다");
        }

        //예약저장
        reservationService.saveReservation(reservationDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity.ok().headers(headers).body("예약정보가 성공적으로 저장되었습니다.");
    }

    @PostMapping("/admin/login")
    public ResponseEntity<String> adminLogin(@RequestBody AdminLoginDTO adminLoginDTO) {
        boolean isAuthenticated = memberManagerService.authenticateAdmin(adminLoginDTO);

        if (isAuthenticated) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }
}
