package com.example.gonggang.domain.appointment.application;

import com.example.gonggang.domain.appointment.domain.AppointmentParticipant;
import com.example.gonggang.domain.appointment.repository.AppointmentParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentParticipantSaveService {
    private final AppointmentParticipantRepository appointmentParticipantRepository;

    public void save(AppointmentParticipant appointmentParticipant) {
        appointmentParticipantRepository.save(appointmentParticipant);
    }
}
