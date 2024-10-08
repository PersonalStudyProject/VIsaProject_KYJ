package com.ohgiraffers.visaproject.model.service;

import com.ohgiraffers.visaproject.model.dto.ApplyDTO;
import com.ohgiraffers.visaproject.model.entity.ApplyEntity;
import com.ohgiraffers.visaproject.model.repository.ApplyRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplyService {

    private ApplyRepository applyRepository;
//    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public ApplyService(ApplyRepository applyRepository){
//    public ApplyService(ApplyRepository applyRepository, EntityManagerFactory entityManagerFactory) {

        this.applyRepository = applyRepository;
//        this.entityManagerFactory = entityManagerFactory;

    }

//    @Transactional
//    public void someBusinessMethod() {
//        // 비즈니스 로직
//    }

    public List<ApplyDTO> getAllApplys() {

        List<ApplyEntity> applys = applyRepository.findAll();

        if(applys.isEmpty()){
            log.warn("No data found in applyRepository");
        }else {
            log.info("Found{} applications in the repository", applys.size());
        }

        return applys.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ApplyDTO convertToDTO(ApplyEntity applyeentity){
        //엔티티를 dto로 변환하는 로직
        ApplyDTO applyDTO = new ApplyDTO();
        applyDTO.setApplyNo(applyeentity.getApplyNo());
        applyDTO.setApplicant(applyeentity.getApplicant());
        applyDTO.setApplyDate(applyeentity.getApplyDate());
        applyDTO.setDocument(applyeentity.getDocument());
        applyDTO.setReason(applyeentity.getReason());
        return applyDTO;
    }

    public ApplyDTO saveApply(ApplyDTO applyDTO) {

        //Applydto를 applyentity로 변환
        ApplyEntity applyEntity = convertToEntity(applyDTO);

        //applyentity를 데이터베이스에 저장
        ApplyEntity savedEntity = applyRepository.save(applyEntity);

        //저장된 applyentity를 applydto로 변환
        return convertToDTO(savedEntity);
    }

    private ApplyEntity convertToEntity(ApplyDTO applyDTO) {
        ApplyEntity applyEntity = new ApplyEntity();
        applyEntity.setApplyNo(applyDTO.getApplyNo());
        applyEntity.setApplicant(applyDTO.getApplicant());
        applyEntity.setApplyDate(applyDTO.getApplyDate());
        applyEntity.setDocument(applyDTO.getDocument());
        applyEntity.setReason(applyDTO.getReason());
        return applyEntity;

    }

    public void UpdateApply(Long applyNo, ApplyDTO modifyInfo) {

        // No에 해당하는 게시글을 찾음
        Optional<ApplyEntity> applyOptional = applyRepository.findById(applyNo);

        if (applyOptional.isPresent()) {
            ApplyEntity existingApply = applyOptional.get();

            // 게시글 내용을 수정함
            existingApply.setApplicant(modifyInfo.getApplicant());
            existingApply.setReason(modifyInfo.getReason());

            // 수정된 게시글을 저장함
            applyRepository.save(existingApply);

        } else {
            // postNo에 해당하는 게시글이 없을 경우 예외를 던질 수도 있음
            throw new EntityNotFoundException("Apply not found with ID: " + applyNo);
        }

    }

    public void deleteApply(Long applyNo) {

        if (applyRepository.existsById(applyNo)) {
            applyRepository.deleteById(applyNo);
        } else {
            throw new EntityNotFoundException("apply not found with ID: " + applyNo);
        }

    }

    public List<ApplyDTO> findApplysByCriteria(Long applyNo, String applicant, String reason) {
        List<ApplyEntity> applyEntities;

        // applyNo로 검색
        if (applyNo != null) {
            applyEntities = applyRepository.findByApplyNo(applyNo);
        }
        // applicant와 reason으로 검색
        else if (applicant != null && reason != null) {
            applyEntities = applyRepository.findByApplicantAndReason(applicant, reason);
        }
        // applicant로 검색
        else if (applicant != null) {
            applyEntities = applyRepository.findByApplicant(applicant);
        }
        // reason으로 검색
        else if (reason != null) {
            applyEntities = applyRepository.findByReason(reason);
        }
        // 모든 조건이 없으면 전체 조회
        else {
            applyEntities = applyRepository.findAll();
        }
        // DTO로 변환
        return applyEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }


}
