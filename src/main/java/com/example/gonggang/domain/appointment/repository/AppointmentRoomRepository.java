package com.example.gonggang.domain.appointment.repository;

import com.example.gonggang.domain.appointment.domain.AppointmentRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRoomRepository extends JpaRepository<AppointmentRoom, Long> {
    Optional<AppointmentRoom> findByEntranceCode(String enteranceCode);
}
