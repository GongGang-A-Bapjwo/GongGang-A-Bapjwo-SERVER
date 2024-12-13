package com.example.gonggang.domain.free_time.domain;

import java.time.LocalTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.gonggang.domain.common.BaseTimeEntity;
import com.example.gonggang.domain.common.Weekday;
import com.example.gonggang.domain.users.domain.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeTime extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalTime startTime;

	@Column
	private LocalTime endTime;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Weekday weekday;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Users user;

	@Builder
	private FreeTime(
		final LocalTime startTime,
		final LocalTime endTime,
		final Weekday weekday,
		final Users user
	) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.weekday = weekday;
		this.user = user;
	}

	public static FreeTime create(
		final LocalTime startTime,
		final LocalTime endTime,
		final Weekday weekday,
		final Users user
	) {
		return FreeTime.builder()
			.startTime(startTime)
			.endTime(endTime)
			.weekday(weekday)
			.user(user)
			.build();
	}
}
