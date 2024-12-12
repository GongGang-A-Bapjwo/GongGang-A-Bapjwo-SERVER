package com.example.gonggang.domain.free_time.dto.response;

public record FreeTimeResponse(
	String weekday,
	String startTime,
	String endTime,
	Long userId
) {
	public static FreeTimeResponse of(String weekday, String startTime, String endTime, Long userId) {
		return new FreeTimeResponse(weekday, startTime, endTime, userId);
	}
}
