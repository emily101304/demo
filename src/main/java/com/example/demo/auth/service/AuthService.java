package com.example.demo.auth.service;

import com.example.demo.auth.dto.AuthSigninRequestDto;
import com.example.demo.auth.dto.AuthSigninResponseDto;
import com.example.demo.auth.dto.AuthSignupRequestDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    public final MemberRepository memberRepository;

    @Transactional
    public void signup(AuthSignupRequestDto dto) {
        Member member = new Member(dto.getEmail());
        memberRepository.save(member);
    }

    public AuthSigninResponseDto login(AuthSigninRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 멤버는 존재하지 않습니다!")
        );
        return new AuthSigninResponseDto(member.getId());
    }
}
