package com.example.gonggang.domain.free_time.dto.response;

import com.example.gonggang.domain.free_time.domain.FreeTime;

public record FreeTimeItem(
        String startTime,
        String endTime
) {
    public static FreeTimeItem of(FreeTime freeTime) {
        return new FreeTimeItem(
                freeTime.getStartTime().toString(),
                freeTime.getEndTime().toString()
        );
    }
}
