package com.example.gonggang.domain.appointment.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.gonggang.domain.common.BaseTimeEntity;
import com.example.gonggang.domain.users.domain.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppointmentParticipant extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private boolean isOwner;

	@Column(nullable = false)
	private boolean hasLeft;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "participants_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users participant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appointment_room_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private AppointmentRoom appointmentRoom;

	@Builder
	private AppointmentParticipant(
		final Users participant,
		final AppointmentRoom appointmentRoom,
		final boolean isOwner,
		final boolean hasLeft
	) {
		this.participant = participant;
		this.appointmentRoom = appointmentRoom;
		this.isOwner = isOwner;
		this.hasLeft = hasLeft;
	}

	public static AppointmentParticipant create(
		final Users participant,
		final AppointmentRoom appointmentRoom,
		final boolean isOwner,
		final boolean hasLeft
	) {
		return AppointmentParticipant.builder()
			.participant(participant)
			.appointmentRoom(appointmentRoom)
			.isOwner(isOwner)
			.hasLeft(hasLeft)
			.build();
	}
}
