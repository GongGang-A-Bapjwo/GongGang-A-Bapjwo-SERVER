package com.example.gonggang.domain.appointment.repository;

import com.example.gonggang.domain.appointment.domain.AppointmentBoard;
import com.example.gonggang.domain.common.Weekday;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentBoardRepository extends JpaRepository<AppointmentBoard,Long> {
    List<AppointmentBoard> findAllByStartTimeAndEndTimeAndWeekday(LocalTime startTime, LocalTime endTime,
                                                                  Weekday weekday);

    List<AppointmentBoard> findAll();
}
