package com.example.gonggang.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Weekday {
	MONDAY(1),
	TUESDAY(2),
	WEDNESDAY(3),
	THURSDAY(4),
	FRIDAY(5),
	SATURDAY(6),
	SUNDAY(7);

	private final int value;
}
