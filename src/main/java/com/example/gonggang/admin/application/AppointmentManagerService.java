package com.example.gonggang.admin.application;

import com.example.gonggang.domain.appointment.application.AppointmentBoardGetService;
import com.example.gonggang.domain.appointment.application.AppointmentDeleteService;
import com.example.gonggang.domain.appointment.domain.AppointmentBoard;
import com.example.gonggang.domain.appointment.dto.response.AllAppointmentBoardResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentManagerService {

    private final AppointmentBoardGetService appointmentBoardGetService;
    private final AppointmentDeleteService appointmentDeleteService;

    @Transactional(readOnly = true)
    public AllAppointmentBoardResponse readAll() {
        List<AppointmentBoard> appointmentBoards = appointmentBoardGetService.findAllBoard();
        return AllAppointmentBoardResponse.toResponse(
                appointmentBoards
        );
    }

    @Transactional
    public void delete(long boardId) {
        AppointmentBoard appointmentBoard = appointmentBoardGetService.findById(boardId);
        appointmentDeleteService.delete(appointmentBoard);
    }
}
