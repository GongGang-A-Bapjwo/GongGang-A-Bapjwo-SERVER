package com.example.gonggang.admin.api;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gonggang.admin.application.AdminAuthService;
import com.example.gonggang.admin.application.dto.request.AdminLoginRequest;
import com.example.gonggang.admin.application.dto.request.AdminRegisterRequest;
import com.example.gonggang.domain.member.dto.LoginSuccessResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController implements AdminApi {

	private static final int COOKIE_MAX_AGE = 7 * 24 * 60 * 60;
	private static final String REFRESH_TOKEN = "refreshToken";
	private final AdminAuthService adminAuthService;

	@Override
	public ResponseEntity<String> register(AdminRegisterRequest request) {
		return ResponseEntity.ok().body(adminAuthService.register(request));
	}

	@Override
	public ResponseEntity<LoginSuccessResponse> login(AdminLoginRequest request, HttpServletResponse response) {
		LoginSuccessResponse loginSuccessResponse = adminAuthService.login(request.id(), request.password());
		ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, loginSuccessResponse.refreshToken())
			.maxAge(COOKIE_MAX_AGE)
			.path("/")
			.secure(true)
			.sameSite("None")
			.httpOnly(true)
			.build();
		response.setHeader("Set-Cookie", cookie.toString());
		return ResponseEntity.ok()
			.body(LoginSuccessResponse.of(loginSuccessResponse.accessToken(), null, loginSuccessResponse.nickname(),
				loginSuccessResponse.role()));
	}

	@Override
	public ResponseEntity<String> healthCheck(){
		return ResponseEntity.ok().body("ok");
	}
}
