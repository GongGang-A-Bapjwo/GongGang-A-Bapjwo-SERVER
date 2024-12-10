package com.example.gonggang.domain.appointment.dto.response;

public record AppointmentCreateResponse(
        String entranceCode
) {
    public static AppointmentCreateResponse toResponse(String entranceCode) {
        return new AppointmentCreateResponse(entranceCode);
    }
}
