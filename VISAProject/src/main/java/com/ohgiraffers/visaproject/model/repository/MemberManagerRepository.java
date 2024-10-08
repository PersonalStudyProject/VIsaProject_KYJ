package com.ohgiraffers.visaproject.model.repository;

import com.ohgiraffers.visaproject.model.entity.ApplyEntity;
import com.ohgiraffers.visaproject.model.entity.MemberManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberManagerRepository extends JpaRepository <MemberManagerEntity, Long>{

    Optional<MemberManagerEntity> findByUsername(String username);
}
