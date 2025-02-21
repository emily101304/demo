package com.example.demo.auth.controller;

import com.example.demo.auth.dto.AuthSigninRequestDto;
import com.example.demo.auth.dto.AuthSigninResponseDto;
import com.example.demo.auth.dto.AuthSignupRequestDto;
import com.example.demo.auth.service.AuthService;
import com.example.demo.common.consts.Const;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public void signup(@RequestBody AuthSignupRequestDto dto) {
        Member member = new Member(dto.getEmail());
        memberRepository.save(member);
    }

    @PostMapping("/signin")
    public void signin(@RequestBody AuthSigninRequestDto dto, HttpServletRequest request) {
        AuthSigninResponseDto result = authService.login(dto);

        HttpSession session = request.getSession(); // 신규 세션 생성, JSESSIONID 쿠키 발급
        session.setAttribute(Const.LOGIN_Member, result.getMemberId()); // 서버 메모리에 세션 저장
    }

    @PostMapping("/signout")
    public void singout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session !=null) {
            session.invalidate();
        }
    }
}
