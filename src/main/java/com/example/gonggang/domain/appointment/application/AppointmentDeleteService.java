package com.example.gonggang.domain.appointment.application;

import com.example.gonggang.domain.appointment.domain.AppointmentBoard;
import com.example.gonggang.domain.appointment.repository.AppointmentBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentDeleteService {
    private final AppointmentBoardRepository appointmentBoardRepository;

    public void delete(AppointmentBoard appointmentBoard) {
        appointmentBoardRepository.delete(appointmentBoard);
    }
}
