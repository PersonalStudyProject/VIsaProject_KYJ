package com.ohgiraffers.visaproject.model.repository;

import com.ohgiraffers.visaproject.model.entity.ApplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyRepository extends JpaRepository<ApplyEntity, Long> {

    List<ApplyEntity> findByApplyNo(Long applyNo);

    List<ApplyEntity> findByApplicant(String applicant);

    List<ApplyEntity> findByReason(String reason);

    List<ApplyEntity> findByApplicantAndReason(String applicant, String reason);

}
