package com.example.gonggang.admin.api;

import com.example.gonggang.admin.application.AppointmentManagerService;
import com.example.gonggang.domain.appointment.application.AppointmentBoardGetService;
import com.example.gonggang.domain.appointment.application.AppointmentManageService;
import com.example.gonggang.domain.appointment.dto.response.AllAppointmentBoardResponse;
import com.example.gonggang.global.config.success.SuccessCode;
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

	@GetMapping("/get-members")
	public ResponseEntity<List<ReadAllMemberResponse>> findAll() {
		List<ReadAllMemberResponse> response = userManageService.readAll();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/delete/{email}")
	public ResponseEntity<String> delete(@PathVariable String email) {
		userManageService.delete(email);
		return ResponseEntity.ok(SuccessCode.DELETE_SUCCESS.getMessage());
	}

	@GetMapping("get-appointmentBoards")
	public ResponseEntity<AllAppointmentBoardResponse> readAll() {
		AllAppointmentBoardResponse response = appointmentManagerService.readAll();
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{boardId}")
	public ResponseEntity<String> delete(@PathVariable Long boardId) {
		appointmentManagerService.delete(boardId);
		return ResponseEntity.ok(SuccessCode.DELETE_SUCCESS.getMessage());
	}
}
