package com.example.gonggang.domain.member.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.gonggang.domain.common.BaseTimeEntity;
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
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull
	private String nickname;

	@Column
	private String email;

	@Column
	private LocalDateTime deletedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@NotNull
	private Users user;

	@Column
	@NotNull
	private Long socialId;

	@Enumerated(EnumType.STRING)
	private SocialType socialType;

	@Column
	@NotNull
	private String timeTableImageUrl;

	@Column
	@NotNull
	private RegisterType registerType;

	@Builder
	private Member(
		final String nickname,
		final String email,
		final LocalDateTime deletedAt,
		final Users user,
		final Long socialId,
		final SocialType socialType,
		final String timeTableImageUrl,
		final RegisterType registerType
	) {
		this.nickname = nickname;
		this.email = email;
		this.deletedAt = deletedAt;
		this.user = user;
		this.socialId = socialId;
		this.socialType = socialType;
		this.timeTableImageUrl = timeTableImageUrl;
		this.registerType = registerType;
	}

	public static Member create(final String nickname,
		final String email,
		final Users user,
		final Long socialId,
		final SocialType socialType,
		final String timeTableImageUrl,
		final RegisterType registerType
	) {
		return Member.builder()
			.nickname(nickname)
			.email(email)
			.user(user)
			.socialId(socialId)
			.socialType(socialType)
			.timeTableImageUrl(timeTableImageUrl)
			.registerType(registerType)
			.build();
	}
}
