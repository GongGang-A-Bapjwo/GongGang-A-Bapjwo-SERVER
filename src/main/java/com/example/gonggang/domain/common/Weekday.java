package com.example.gonggang.domain.common;

import com.example.gonggang.domain.common.exception.InvalidWeekdayException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Weekday {
	MONDAY("월"),
	TUESDAY("화"),
	WEDNESDAY("수"),
	THURSDAY("목"),
	FRIDAY("금"),
	SATURDAY("토"),
	SUNDAY("일");

	private final String value;

	public static Weekday fromKorean(String koreanDay) {
		for (Weekday day : Weekday.values()) {
			if (day.getValue().equals(koreanDay)) {
				return day;
			}
		}
		throw new InvalidWeekdayException();
	}

	public static Weekday fromEnglish(String englishDay) {
			return Weekday.valueOf(englishDay.toUpperCase());
	}
}
