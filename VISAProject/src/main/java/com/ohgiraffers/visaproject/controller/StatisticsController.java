package com.ohgiraffers.visaproject.controller;

import com.ohgiraffers.visaproject.model.dto.StatisticsDTO;
import com.ohgiraffers.visaproject.model.service.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    //통계정보를 조회 엔드포인트
    @GetMapping("/calculate")
    public ResponseEntity<StatisticsDTO> getStatistics(){
        StatisticsDTO statisticsDTO = statisticsService.calculateStatistics();  //통계 계산 및 저장
        return new ResponseEntity<>(statisticsDTO, HttpStatus.OK);  //계산된 통계정보를 반환
    }

}
