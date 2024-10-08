package com.ohgiraffers.visaproject.model.service;

import com.ohgiraffers.visaproject.model.dto.StatisticsDTO;
import com.ohgiraffers.visaproject.model.entity.Statistics;
import com.ohgiraffers.visaproject.model.repository.ApplyRepository;
import com.ohgiraffers.visaproject.model.repository.MemberManagerRepository;
import com.ohgiraffers.visaproject.model.repository.ReservationRepository;
import com.ohgiraffers.visaproject.model.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final MemberManagerRepository memberManagerRepository;
    private final ApplyRepository applyRepository;
    private final ReservationRepository reservationRepository;

    public StatisticsService(StatisticsRepository statisticsRepository,
                             MemberManagerRepository memberManagerRepository,
                             ApplyRepository applyRepository,
                             ReservationRepository reservationRepository) {
        this.statisticsRepository = statisticsRepository;
        this.memberManagerRepository = memberManagerRepository;
        this.applyRepository = applyRepository;
        this.reservationRepository = reservationRepository;
    }

    //통계 계산 메서드
    public StatisticsDTO calculateStatistics() {
        List<Statistics> allData = statisticsRepository.findAll(); //전체 데이터를 가져옴

        Long totalCount = (long) allData.size();
        Long applyCount = applyRepository.count();
        Long memberCount = memberManagerRepository.count();
        Long reservationCount = reservationRepository.count();

        //사용자당 평균예약수 계산
        Double averageReservationPerMember = (memberCount > 0) ? reservationCount / (double) memberCount : 0.0;

        //이전통계 데이터 불러오기 (db에서 전체 데이터조회)
//        List<Statistics> allData = statisticsRepository.findAll();
        //        Long totalCount = (long) allData.size();
//        Double averageValue = allData.stream()
//                .mapToDouble(stat -> stat.getAverageValue() != null ? stat.getAverageValue() : 0.0)
//                .average()
//                .orElse(0.0);

        //이전에 저장된 평균값을 계산
        Double averageValue = allData.stream()
                .mapToDouble(stat -> stat.getAverageValue() != null ? stat.getAverageValue() : 0.0)
                .average()
                .orElse(0.0);

        //null값을 처리하기 위해 optional사용
//        Double averageValue = allData.stream()
////                .mapToDouble(Statistics::getAverageValue)
//                .mapToDouble(stat -> stat.getAverageValue() != null ? stat.getAverageValue() : 0.0)
//                .average()
//                .orElse(0.0);  //평균값계산

        //새로운 통계정보 생성및 저장
        Statistics newStatistics = new Statistics(null, totalCount, applyCount, totalCount, reservationCount, averageReservationPerMember, averageValue);
        statisticsRepository.save(newStatistics);

        return new StatisticsDTO(totalCount, applyCount, totalCount, reservationCount, averageReservationPerMember, averageValue); //dto로 반환
    }
}
