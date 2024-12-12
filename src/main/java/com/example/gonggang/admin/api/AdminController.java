package com.example.gonggang.admin.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gonggang.admin.application.AdminAuthService;
import com.example.gonggang.admin.application.UserManageService;
import com.example.gonggang.admin.application.dto.request.AdminLoginRequest;
import com.example.gonggang.admin.application.dto.request.AdminRegisterRequest;
import com.example.gonggang.admin.application.dto.response.ReadAllMemberResponse;
import com.example.gonggang.domain.member.dto.LoginSuccessResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController implements AdminApi {

	private final AdminAuthService adminAuthService;
	private final UserManageService userManageService;

	@Override
	public ResponseEntity<String> register(AdminRegisterRequest request) {
		return ResponseEntity.ok().body(adminAuthService.register(request));
	}

	@Override
	public ResponseEntity<LoginSuccessResponse> login(AdminLoginRequest request, HttpServletResponse response) {
		LoginSuccessResponse loginSuccessResponse = adminAuthService.login(request.id(), request.password());
		return ResponseEntity.ok().body(loginSuccessResponse);
	}

	@Override
	public ResponseEntity<String> healthCheck(){
		return ResponseEntity.ok().body("ok");
	}

	@GetMapping("/get-members")
	public ResponseEntity<List<ReadAllMemberResponse>> findAll() {
		List<ReadAllMemberResponse> response = userManageService.readAll();
		return ResponseEntity.ok(response);
	}
}
