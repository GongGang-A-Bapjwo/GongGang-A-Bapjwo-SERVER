package com.example.gonggang.global.auth.client.dto;

import com.example.gonggang.domain.member.domain.SocialType;

public record MemberInfoResponse(
	Long socialId,
	String nickname,
	String email,
	SocialType socialType
) {
	public static MemberInfoResponse of(
		final Long socialId,
		final String nickname,
		final String email,
		final SocialType socialType
	) {
		return new MemberInfoResponse(socialId, nickname, email, socialType);
	}
}
