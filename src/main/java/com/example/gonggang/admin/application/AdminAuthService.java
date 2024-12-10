package com.example.gonggang.admin.application;

import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.gonggang.admin.application.dto.request.AdminRegisterRequest;
import com.example.gonggang.admin.domain.Admin;
import com.example.gonggang.admin.exception.AdminNotFoundException;
import com.example.gonggang.admin.exception.PasswordNotMatchException;
import com.example.gonggang.admin.repository.AdminRepository;
import com.example.gonggang.domain.member.dto.LoginSuccessResponse;
import com.example.gonggang.domain.users.domain.Role;
import com.example.gonggang.global.auth.jwt.provider.JwtTokenProvider;
import com.example.gonggang.global.config.success.SuccessCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

	private final JwtTokenProvider jwtTokenProvider;
	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;

	public String register(AdminRegisterRequest request) {
		Admin admin = Admin.create(request.username(), passwordEncoder.encode(request.password()));
		adminRepository.save(admin);

		return SuccessCode.ADMIN_REGISTER_SUCCESS.getMessage();
	}

	public LoginSuccessResponse login(String username, String password) {
		Admin admin = adminRepository.findByUsername(username).orElseThrow(AdminNotFoundException::new);
		validatePassword(password, admin);

		Authentication authentication = new UsernamePasswordAuthenticationToken(
			admin.getId(), null, Collections.singletonList(new SimpleGrantedAuthority(Role.ADMIN.getRoleName()))
		);

		String accessToken = jwtTokenProvider.issueAccessToken(authentication);
		String refreshToken = jwtTokenProvider.issueRefreshToken(authentication);

		return LoginSuccessResponse.of(accessToken, refreshToken, username, Role.ADMIN.getRoleName());
	}

	private void validatePassword(String password, Admin admin) {
		if (!passwordEncoder.matches(password, admin.getAdminPassword())) {
			throw new PasswordNotMatchException();
		}
	}
}
