package com.example.gonggang.domain.appointment.repository;

import com.example.gonggang.domain.appointment.domain.AppointmentBoard;
import com.example.gonggang.domain.common.Weekday;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentBoardRepository extends JpaRepository<AppointmentBoard,Long> {
    List<AppointmentBoard> findAllByWeekdayAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(
            Weekday weekday, LocalTime endTime, LocalTime startTime);

    List<AppointmentBoard> findAll();

    Optional<AppointmentBoard> findById(long id);
}
