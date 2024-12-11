package com.example.gonggang.domain.appointment.application;

import com.example.gonggang.domain.appointment.domain.AppointmentRoom;
import com.example.gonggang.domain.appointment.exception.AppointmentRoomNotFoundException;
import com.example.gonggang.domain.appointment.repository.AppointmentRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentRoomGetService {
    private final AppointmentRoomRepository appointmentRoomRepository;

    public AppointmentRoom findByEnteranceCode(String enteranceCode) {
        AppointmentRoom appointmentRoom = appointmentRoomRepository.findByEntranceCode(enteranceCode).orElseThrow(
                AppointmentRoomNotFoundException::new);

        return appointmentRoom;
    }

    public AppointmentRoom findByRoomId(long roomId) {
        AppointmentRoom appointmentRoom = appointmentRoomRepository.findById(roomId).orElseThrow(
                AppointmentRoomNotFoundException::new);

        return appointmentRoom;
    }
}
