package com.example.gonggang.domain.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
	KAKAO("KAKAO")
	;
	private final String type;
}
