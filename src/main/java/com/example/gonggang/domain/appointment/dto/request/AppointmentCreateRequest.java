package com.example.gonggang.domain.appointment.dto.request;

import com.example.gonggang.domain.common.Category;

public record AppointmentCreateRequest(
        Category category,
        String title,
        int maxParticipants
) {
}
