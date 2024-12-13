package com.example.gonggang.domain.appointment.dto.response;


import com.example.gonggang.domain.appointment.domain.AppointmentBoard;
import java.util.List;

public record AllAppointmentRoomResponse (
        List<AppointmentBoard> AppointmentBoard
) {
    public static AllAppointmentRoomResponse toResponse(List<AppointmentBoard> appointmentBoards) {
        return new AllAppointmentRoomResponse(
                appointmentBoards
        );
    }
}
