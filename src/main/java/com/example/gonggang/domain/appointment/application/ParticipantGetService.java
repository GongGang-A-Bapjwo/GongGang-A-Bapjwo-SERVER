package com.example.gonggang.domain.appointment.application;

import com.example.gonggang.domain.appointment.domain.AppointmentParticipant;
import com.example.gonggang.domain.appointment.domain.AppointmentRoom;
import com.example.gonggang.domain.appointment.exception.ParticipantNotFoundException;
import com.example.gonggang.domain.appointment.repository.AppointmentParticipantRepository;
import com.example.gonggang.domain.users.domain.Users;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantGetService {
    private final AppointmentParticipantRepository appointmentParticipantRepository;

    public boolean checkAlreadyEntered(Users users, AppointmentRoom appointmentRoom) {
        return appointmentParticipantRepository.existsByParticipantAndAppointmentRoom(users, appointmentRoom);
    }

    public List<AppointmentParticipant> findAllByParticipants(Users user) {
        return appointmentParticipantRepository.findAllByParticipantAndNotLeft(user);
    }

    public AppointmentParticipant findByParticipantAndRoom(Users user, Long roomId) {
        return appointmentParticipantRepository.findByParticipantAndRoomId(user,roomId).orElseThrow(
                ParticipantNotFoundException::new);
    }
}
