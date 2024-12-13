package com.example.gonggang.domain.appointment.dto.response;


import com.example.gonggang.domain.appointment.domain.AppointmentBoard;
import java.util.List;

public record AllAppointmentBoardResponse(
        List<AppointmentBoard> AppointmentBoard
) {
    public static AllAppointmentBoardResponse toResponse(List<AppointmentBoard> appointmentBoards) {
        return new AllAppointmentBoardResponse(
                appointmentBoards
        );
    }
}
