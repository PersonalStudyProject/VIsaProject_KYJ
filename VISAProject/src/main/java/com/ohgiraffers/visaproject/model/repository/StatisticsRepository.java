package com.ohgiraffers.visaproject.model.repository;

import com.ohgiraffers.visaproject.model.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository <Statistics, Long> {

}
