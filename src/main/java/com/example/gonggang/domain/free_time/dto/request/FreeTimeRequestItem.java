package com.example.gonggang.domain.free_time.dto.request;

public record FreeTimeRequestItem(
        String weekday,
        String startTime,
        String endTime
) {
}
