package com.example.gonggang.domain.appointment.repository;

import com.example.gonggang.domain.appointment.domain.AppointmentParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentParticipantRepository extends JpaRepository<AppointmentParticipant, Long> {
}
