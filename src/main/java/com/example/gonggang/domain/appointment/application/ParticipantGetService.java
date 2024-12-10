package com.example.gonggang.domain.appointment.application;

import com.example.gonggang.domain.appointment.domain.AppointmentParticipant;
import com.example.gonggang.domain.appointment.repository.AppointmentParticipantRepository;
import com.example.gonggang.domain.appointment.repository.AppointmentRoomRepository;
import com.example.gonggang.domain.users.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantGetService {
    private final AppointmentParticipantRepository appointmentParticipantRepository;

    public boolean checkAlreadyEntered(Users users) {
        return appointmentParticipantRepository.existsByParticipant(users);
    }
}
