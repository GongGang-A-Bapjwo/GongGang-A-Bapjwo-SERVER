package com.example.gonggang.domain.member.dto;

public record LoginSuccessResponse(
	String accessToken,
	String refreshToken,
	String nickname,
	String role,
	Boolean hasFreeTime
) {
	public static LoginSuccessResponse of(
		final String accessToken,
		final String refreshToken,
		final String nickname,
		final String role,
		final Boolean hasFreeTime
	) {
		return new LoginSuccessResponse(accessToken, refreshToken, nickname, role, hasFreeTime);
	}
}
