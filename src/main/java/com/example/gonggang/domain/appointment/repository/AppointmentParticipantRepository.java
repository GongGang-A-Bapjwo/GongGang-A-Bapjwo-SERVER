package com.example.gonggang.domain.appointment.repository;

import com.example.gonggang.domain.appointment.domain.AppointmentParticipant;
import com.example.gonggang.domain.users.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentParticipantRepository extends JpaRepository<AppointmentParticipant, Long> {
    boolean existsByParticipant(Users participant);
}
