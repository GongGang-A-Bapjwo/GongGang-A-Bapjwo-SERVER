package com.example.gonggang.domain.appointment.application;

import com.example.gonggang.domain.appointment.domain.AppointmentBoard;
import com.example.gonggang.domain.appointment.exception.AppointmentBoardNotFoundException;
import com.example.gonggang.domain.appointment.repository.AppointmentBoardRepository;
import com.example.gonggang.domain.free_time.domain.FreeTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentBoardGetService {
    private final AppointmentBoardRepository appointmentBoardRepository;

    public List<AppointmentBoard> findAllBoard(FreeTime freeTime) {
        return appointmentBoardRepository.findAllByWeekdayAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(freeTime.getWeekday(), freeTime.getStartTime(),freeTime.getEndTime());
    }

    public List<AppointmentBoard> findAllBoard() {
        return appointmentBoardRepository.findAll();
    }

    public AppointmentBoard findById(long id) {
        return appointmentBoardRepository.findById(id).orElseThrow(AppointmentBoardNotFoundException::new);
    }
}
