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
import java.util.UUID;
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

	@Column(nullable = false)
	private int maxParticipants;

	@Column(nullable = false)
	private int currentParticipants;

	@Column
	private LocalTime decidedStartTime;

	@Column
	private LocalTime decidedEndTime;

	@Enumerated(EnumType.STRING)
	@Column
	private Weekday decidedWeekday;

	@Column(length = 10, nullable = false)
	private String entranceCode;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;

	@Column(length = 20, nullable = false)
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

	public static AppointmentRoom initCreate(
			final int maxParticipants,
			final String entranceCode,
			final Category category,
			final String title
	) {
		return AppointmentRoom.builder()
				.maxParticipants(maxParticipants)
				.currentParticipants(1)
				.decidedStartTime(null)
				.decidedEndTime(null)
				.decidedWeekday(null)
				.entranceCode(entranceCode)
				.category(category)
				.title(title)
				.build();
	}

	public static String generateEntranceCode() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid.substring(0, 10);
	}

	public boolean isAvailable() {
		return this.maxParticipants >= this.currentParticipants + 1;
	}

	public void minusCurrentParticipants() {
		this.currentParticipants -= 1;
	}

	public void plusCurrentParticipants() {
		this.currentParticipants += 1;
	}
}
