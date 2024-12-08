package com.example.gonggang.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
	STUDY("공부"),
	EAT("식사"),
	COUNSELING("상담"),
	ETC("기타")
	;

	private final String description;
}
