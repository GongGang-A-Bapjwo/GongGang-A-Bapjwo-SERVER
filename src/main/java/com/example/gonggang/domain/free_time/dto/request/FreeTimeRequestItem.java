package com.example.gonggang.domain.free_time.dto.request;

import com.example.gonggang.domain.common.Weekday;

public record FreeTimeRequestItem(
        Weekday weekday,
        String startTime,
        String endTime
) {
}
