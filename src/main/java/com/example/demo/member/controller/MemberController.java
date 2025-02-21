package com.example.demo.member.controller;

import com.example.demo.common.consts.Const;
import com.example.demo.member.dto.request.MemberUpdateRequestDto;
import com.example.demo.member.dto.response.MemberResponseDto;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> getAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponseDto> getOne(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.findById(memberId));
    }

    @PutMapping("/members")
    public void update(
            @SessionAttribute(name = Const.LOGIN_Member) Long memberId,
            @RequestBody MemberUpdateRequestDto dto
    ) {
        memberService.update(memberId, dto);
    }

    @DeleteMapping("/members/{memberId}")
    public void delete(
            @SessionAttribute(name = Const.LOGIN_Member) Long memberId
    ) {
        memberService.deleteById(memberId);
    }
}
