package com.example.gonggang.domain.appointment.api;

import static com.example.gonggang.global.config.success.SuccessCode.ENTER_SUCCESS;

import com.example.gonggang.domain.appointment.application.AppointmentManageService;
import com.example.gonggang.domain.appointment.dto.request.AppointmentCreateRequest;
import com.example.gonggang.domain.appointment.dto.request.AppointmentEnterRequest;
import com.example.gonggang.domain.appointment.dto.response.AppointmentAllResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentCreateResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentRemainingResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentsGetResponse;
import com.example.gonggang.domain.external.fast_api.application.FastApiService;
import com.example.gonggang.global.auth.annotation.CurrentMember;
import com.example.gonggang.global.config.success.SuccessCode;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
public class AppointmentController {
	private final AppointmentManageService appointmentManageService;
	private final FastApiService fastApiService;

	@PostMapping()
	public ResponseEntity<AppointmentCreateResponse> create(@CurrentMember Long userId,
		@RequestBody AppointmentCreateRequest appointmentCreateRequest) {
		AppointmentCreateResponse result = appointmentManageService.create(userId, appointmentCreateRequest);

		return ResponseEntity.ok(result);
	}

	@PostMapping("/enter")
	public ResponseEntity<String> create(@CurrentMember Long userId,
		@RequestBody AppointmentEnterRequest appointmentEnterRequest) {

		appointmentManageService.enter(userId, appointmentEnterRequest);
		return ResponseEntity.ok(ENTER_SUCCESS.getMessage());
	}

	@GetMapping
	public ResponseEntity<List<AppointmentsGetResponse>> readAll(@CurrentMember Long userId) {
		List<AppointmentsGetResponse> result = appointmentManageService.readAll(userId);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{roomId}")
	public ResponseEntity<String> delete(@CurrentMember Long userId, @PathVariable Long roomId) {
		appointmentManageService.delete(userId, roomId);
		return ResponseEntity.ok("성공");
	}

	@GetMapping("/remaining-count/{roomId}")
	public ResponseEntity<AppointmentRemainingResponse> read(@CurrentMember Long userId, @PathVariable Long roomId) {
		AppointmentRemainingResponse response = appointmentManageService.read(userId, roomId);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/update-setting/{roomId}")
	public ResponseEntity<String> update(@CurrentMember Long userId, @PathVariable Long roomId,
		@RequestBody AppointmentCreateRequest request) {
		appointmentManageService.update(userId, roomId, request);
		return ResponseEntity.ok(SuccessCode.UPDATE_SUCCESS.getMessage());
	}

	@GetMapping("/all")
	public ResponseEntity<AppointmentAllResponse> read(@CurrentMember Long userId) {
		AppointmentAllResponse response = appointmentManageService.read(userId);
		return ResponseEntity.ok(response);
	}
}
