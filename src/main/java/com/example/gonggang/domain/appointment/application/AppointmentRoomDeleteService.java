package com.example.gonggang.domain.appointment.application;

import com.example.gonggang.domain.appointment.domain.AppointmentRoom;
import com.example.gonggang.domain.appointment.repository.AppointmentRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentRoomDeleteService {
    private final AppointmentRoomRepository appointmentRoomRepository;

    public void delete(AppointmentRoom room) {
        appointmentRoomRepository.delete(room);
    }
}
