package com.example.gonggang.domain.appointment.dto.request;

public record AppointmentSelectRequest(
        long roomId,
        String weekday,
        String startTime,
        String endTime
) {
}
