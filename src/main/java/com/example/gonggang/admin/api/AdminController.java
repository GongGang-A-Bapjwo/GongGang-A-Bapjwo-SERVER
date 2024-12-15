package com.example.gonggang.admin.api;

import com.example.gonggang.admin.application.AppointmentManagerService;
import com.example.gonggang.domain.appointment.application.AppointmentBoardGetService;
import com.example.gonggang.domain.appointment.dto.response.AllAppointmentBoardResponse;
import com.example.gonggang.global.config.success.SuccessCode;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	private final AppointmentManagerService appointmentManagerService;
	private final AppointmentBoardGetService appointmentBoardGetService;

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
	public ResponseEntity<String> healthCheck() {
		return ResponseEntity.ok().body("ok");
	}

	@Operation(summary = "[ADMIN] 회원 조회 API", description = "모든 회원을 조회하는 API입니다.")
	@GetMapping("/member/all")
	public ResponseEntity<List<ReadAllMemberResponse>> findAll() {
		List<ReadAllMemberResponse> response = userManageService.readAll();
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[ADMIN] 회원 탈퇴 API", description = "회원 탈퇴를 위한 API입니다.")
	@DeleteMapping("/user/{email}")
	public ResponseEntity<String> delete(@PathVariable String email) {
		userManageService.delete(email);
		return ResponseEntity.ok(SuccessCode.DELETE_SUCCESS.getMessage());
	}

	@Operation(summary = "[ADMIN] 채팅방 조회 API", description = "모든 채팅방을 조회하는 API입니다.")
	@GetMapping("/appointment-board/all")
	public ResponseEntity<AllAppointmentBoardResponse> readAll() {
		AllAppointmentBoardResponse response = appointmentManagerService.readAll();
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "[ADMIN] 채팅방 삭제 API", description = "boardId를 입력받아 채팅방을 삭제하는 API입니다.")
	@DeleteMapping("/{boardId}")
	public ResponseEntity<String> delete(@PathVariable Long boardId) {
		appointmentManagerService.delete(boardId);
		return ResponseEntity.ok(SuccessCode.DELETE_SUCCESS.getMessage());
	}
}
