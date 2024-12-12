package com.example.gonggang.domain.appointment.dto.response;

import com.example.gonggang.domain.appointment.domain.AppointmentRoom;

public record AppointmentRemainingResponse(
        int currentUserCount,
        int remainingCount
) {
    public static AppointmentRemainingResponse toResponse(AppointmentRoom room) {
        return new AppointmentRemainingResponse(
                room.getMaxParticipants(),
                room.getMaxParticipants() - room.getCurrentParticipants()
        );
    }
}
