package com.example.gonggang.domain.users.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role {
	MEMBER("ROLE_MEMBER"),
	ADMIN("ROLE_ADMIN");

	private final String roleName;

	/**
	 * GrantedAuthority로 변환하는 메서드.
	 * Spring Security에서 사용자 권한을 처리할 때 사용.
	 */
	public GrantedAuthority toGrantedAuthority() {
		return new SimpleGrantedAuthority(roleName);
	}
}
