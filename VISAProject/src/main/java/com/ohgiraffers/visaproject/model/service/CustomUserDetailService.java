package com.ohgiraffers.visaproject.model.service;

import com.ohgiraffers.visaproject.model.entity.MemberManagerEntity;
import com.ohgiraffers.visaproject.model.repository.MemberManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//사용자 이름을 기반으로 userdetails객체를 반환하여 인증프로세스를 지원함
public class CustomUserDetailService implements UserDetailsService {

    //사용자 정보를 데이터베이스에서 조회하는 역할을 하는 리포지토리임
    private final MemberManagerRepository memberRepository;

    @Autowired
    public CustomUserDetailService(MemberManagerRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    //사용자 이름을 기반으로 사용자 정보를 로드함
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //사용자 이름으로 사용자를 조회함, option<membermanagerentity>를 반환함
        MemberManagerEntity member = memberRepository.findByUsername(username)

                //사용자가 존재하지 않을 경우 예외를던짐
                .orElseThrow(() -> new AuthenticationServiceException(username + "은 가입되지 않았습니다."));

        //costomuserdetails를 생성하여 userdetail로 반환함
        return (UserDetails) new CustomUserDetails(member);
    }

}
