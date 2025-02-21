package com.example.demo.auth.dto;

import lombok.Getter;

@Getter
public class AuthSigninResponseDto {

    private final Long memberId;

    public AuthSigninResponseDto(Long memberId) {
        this.memberId = memberId;
    }
}
