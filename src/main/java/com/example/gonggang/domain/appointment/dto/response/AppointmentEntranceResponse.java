package com.example.gonggang.domain.appointment.dto.response;

public record AppointmentEntranceResponse(
	long userId,
	String entranceCode
) {
	public static AppointmentEntranceResponse of(long userId, String entranceCode) {
		return new AppointmentEntranceResponse(userId, entranceCode);
	}
}
