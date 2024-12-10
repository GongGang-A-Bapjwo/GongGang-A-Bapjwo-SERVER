package com.example.gonggang.domain.appointment.dto.response;

import com.example.gonggang.domain.common.Category;
import com.example.gonggang.domain.common.Weekday;
import java.time.LocalTime;

public record AppointmentsGetResponse(
        String title,
        Category category,
        boolean isOwner,
        LocalTime decidedStartTime,
        LocalTime decidedEndTime,
        Weekday decidedWeekday
)
{
}
