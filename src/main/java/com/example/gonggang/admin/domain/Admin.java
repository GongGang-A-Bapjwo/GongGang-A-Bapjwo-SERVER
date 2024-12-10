package com.example.gonggang.admin.domain;

import com.example.gonggang.domain.users.domain.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "admin")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

	@Column(nullable = false)
	Role role;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String adminPassword;
	@Column
	private String refreshToken;

	@Builder
	private Admin(String username, String adminPassword, Role role) {
		this.username = username;
		this.adminPassword = adminPassword;
		this.role = role;
	}

	public static Admin create(String username, String adminPassword) {
		return Admin.builder()
			.username(username)
			.adminPassword(adminPassword)
			.role(Role.ADMIN)
			.build();
	}
}

