package com.example.gonggang.domain.appointment.application;

import com.example.gonggang.domain.appointment.domain.AppointmentRoom;
import com.example.gonggang.domain.appointment.repository.AppointmentRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentRoomSaveService {
    private final AppointmentRoomRepository appointmentRoomRepository;

    public void save(AppointmentRoom appointmentRoom) {
        appointmentRoomRepository.save(appointmentRoom);
    }
}
