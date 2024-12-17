package com.example.gonggang.domain.appointment.dto.response;

import com.example.gonggang.domain.appointment.domain.AppointmentRoom;

public record AppointmentRemainingResponse(
        long roomId,
        int currentUserCount,
        int remainingCount,
        String entranceCode
) {
    public static AppointmentRemainingResponse toResponse(AppointmentRoom room) {
        return new AppointmentRemainingResponse(
                room.getId(),
                room.getCurrentParticipants(),
                room.getMaxParticipants() - room.getCurrentParticipants(),
                room.getEntranceCode()
        );
    }
}
