package com.example.gonggang.domain.external.fast_api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class TimeFormater {
	public static String convertToTimeFormat(String time) {
		int hour = Integer.parseInt(time);
		return String.format("%02d:00", hour);
	}
}
