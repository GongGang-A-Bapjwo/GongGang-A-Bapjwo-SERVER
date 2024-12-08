package com.example.gonggang.domain.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegisterType {
	AUTO("자동"),
	MANUAL("수동");

	private final String displayName;
}
