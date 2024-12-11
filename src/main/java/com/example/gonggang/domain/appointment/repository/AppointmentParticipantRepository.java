package com.example.gonggang.domain.appointment.repository;

import com.example.gonggang.domain.appointment.domain.AppointmentParticipant;
import com.example.gonggang.domain.appointment.domain.AppointmentRoom;
import com.example.gonggang.domain.users.domain.Users;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentParticipantRepository extends JpaRepository<AppointmentParticipant, Long> {
    boolean existsByParticipantAndAppointmentRoom(Users participant, AppointmentRoom appointmentRoom);

    @Query("SELECT ap FROM AppointmentParticipant ap " +
            "JOIN FETCH ap.appointmentRoom ar " +
            "WHERE ap.participant = :participant")
    List<AppointmentParticipant> findAllByParticipantAndNotLeft(@Param("participant") Users participant);

    @Query("SELECT ap FROM AppointmentParticipant ap " +
            "WHERE ap.participant = :participant AND ap.appointmentRoom.id = :roomId")
    Optional<AppointmentParticipant> findByParticipantAndRoomId(
            @Param("participant") Users participant,
            @Param("roomId") Long roomId
    );
}
