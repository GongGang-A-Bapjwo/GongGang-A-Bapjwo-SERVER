package com.example.gonggang.domain.appointment.dto.request;

public record AppointmentExtractTimeRequest(
	String entranceCode,
	Long roomId
) {
}
