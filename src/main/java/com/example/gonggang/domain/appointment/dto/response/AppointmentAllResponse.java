package com.example.gonggang.domain.appointment.dto.response;

import com.example.gonggang.domain.appointment.domain.AppointmentBoard;
import com.example.gonggang.domain.free_time.dto.response.FreeTimeItem;
import com.example.gonggang.domain.free_time.dto.response.FreeTimeResponse;
import java.util.List;

public record AppointmentAllResponse(
        String weekday,
        List<FreeTimeItem> freeTimes,
        List<List<AppointmentBoard>> appointmentBoards
) {
    public static AppointmentAllResponse toResponse(String weekday, List<FreeTimeItem> freeTimes, List<List<AppointmentBoard>> appointmentBoards) {
        return new AppointmentAllResponse(
                weekday,
                freeTimes,
                appointmentBoards
        );
    }
}
