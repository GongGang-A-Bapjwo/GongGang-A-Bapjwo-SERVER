package com.example.gonggang.global.auth.client.dto;

import com.example.gonggang.domain.member.domain.SocialType;

import jakarta.validation.constraints.NotNull;

public record MemberLoginRequest(
	@NotNull(message = "소셜 로그인 종류가 입력되지 않았습니다.")
	SocialType socialType
) {
}
