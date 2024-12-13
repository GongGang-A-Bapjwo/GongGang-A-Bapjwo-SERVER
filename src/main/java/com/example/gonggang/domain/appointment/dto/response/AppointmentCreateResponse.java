package com.example.gonggang.domain.appointment.dto.response;

public record AppointmentCreateResponse(
        long userId,
        String entranceCode
) {
    public static AppointmentCreateResponse toResponse(long userId, String entranceCode) {
        return new AppointmentCreateResponse(userId, entranceCode);
    }
}
