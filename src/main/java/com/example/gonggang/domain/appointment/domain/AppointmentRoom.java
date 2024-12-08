package com.example.gonggang.domain.appointment.domain;

import java.time.LocalTime;

import com.example.gonggang.domain.common.BaseTimeEntity;
import com.example.gonggang.domain.common.Category;
import com.example.gonggang.domain.common.Weekday;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppointmentRoom extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull
	private int maxParticipants;

	@Column
	@NotNull
	private int currentParticipants;

	@Column
	private LocalTime decidedStartTime;

	@Column
	private LocalTime decidedEndTime;

	@Enumerated(EnumType.STRING)
	@Column
	@NotNull
	private Weekday decidedWeekday;

	@Column(length = 10)
	@NotNull
	private String entranceCode;

	@Enumerated(EnumType.STRING)
	@Column
	@NotNull
	private Category category;

	@Column(length = 20)
	@NotNull
	private String title;

	@Builder
	private AppointmentRoom(
		final int maxParticipants,
		final int currentParticipants,
		final LocalTime decidedStartTime,
		final LocalTime decidedEndTime,
		final Weekday decidedWeekday,
		final String entranceCode,
		final Category category,
		final String title
	) {
		this.maxParticipants = maxParticipants;
		this.currentParticipants = currentParticipants;
		this.decidedStartTime = decidedStartTime;
		this.decidedEndTime = decidedEndTime;
		this.decidedWeekday = decidedWeekday;
		this.entranceCode = entranceCode;
		this.category = category;
		this.title = title;
	}

	public static AppointmentRoom create(
		final int maxParticipants,
		final int currentParticipants,
		final LocalTime decidedStartTime,
		final LocalTime decidedEndTime,
		final Weekday decidedWeekday,
		final String entranceCode,
		final Category category,
		final String title
	) {
		return AppointmentRoom.builder()
			.maxParticipants(maxParticipants)
			.currentParticipants(currentParticipants)
			.decidedStartTime(decidedStartTime)
			.decidedEndTime(decidedEndTime)
			.decidedWeekday(decidedWeekday)
			.entranceCode(entranceCode)
			.category(category)
			.title(title)
			.build();
	}
}
