package com.example.gonggang.domain.users.domain;

import com.example.gonggang.domain.common.BaseTimeEntity;

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
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(10) default 'MEMBER'", nullable = false)
	private Role role;

	@Builder
	private Users(Role role) {
		this.role = role;
	}

	public static Users create() {
		return Users.builder()
			.role(Role.MEMBER)
			.build();
	}

	public static Users createWithRole(Role role) {
		return Users.builder()
			.role(role)
			.build();
	}
}
