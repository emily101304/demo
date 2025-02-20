package com.example.demo.member.service;

import com.example.demo.member.dto.request.MemberSaveRequestDto;
import com.example.demo.member.dto.request.MemberUpdateRequestDto;
import com.example.demo.member.dto.response.MemberResponseDto;
import com.example.demo.member.dto.response.MemberSaveResponseDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberSaveResponseDto save(MemberSaveRequestDto dto) {
        Member member = new Member(dto.getEmail());
        Member savedMember = memberRepository.save(member);
        return new MemberSaveResponseDto(
                savedMember.getId(),
                savedMember.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(member -> new MemberResponseDto(member.getId(), member.getEmail())).toList();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("해당 멤버는 존재하지 않습니다!"));
        return new MemberResponseDto(member.getId(), member.getEmail());
    }

    @Transactional
    public void update(Long memberId, MemberUpdateRequestDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("해당 멤버는 존재하지 않습니다!"));
        member.update(dto.getEmail());
    }

    @Transactional
    public void deleteById(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException("해당 멤버는 존재하지 않습니다!");
        }
        memberRepository.deleteById(memberId);
    }
}
