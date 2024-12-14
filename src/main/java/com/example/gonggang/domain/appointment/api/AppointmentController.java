package com.example.gonggang.domain.appointment.api;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gonggang.domain.appointment.application.AppointmentManageService;
import com.example.gonggang.domain.appointment.dto.request.AppointmentCreateRequest;
import com.example.gonggang.domain.appointment.dto.request.AppointmentEnterRequest;
import com.example.gonggang.domain.appointment.dto.request.AppointmentExtractTimeRequest;
import com.example.gonggang.domain.appointment.dto.response.AllAppointmentBoardResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentAllResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentCreateResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentEntranceResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentRemainingResponse;
import com.example.gonggang.domain.appointment.dto.response.AppointmentsGetResponse;
import com.example.gonggang.domain.external.fast_api.application.FastApiService;
import com.example.gonggang.domain.external.fast_api.dto.FastApiResponse;
import com.example.gonggang.global.auth.annotation.CurrentMember;
import com.example.gonggang.global.config.success.SuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
public class AppointmentController {
	private final AppointmentManageService appointmentManageService;
	private final FastApiService fastApiService;

	@PostMapping
	public ResponseEntity<FastApiResponse> create(@CurrentMember Long userId,
		@RequestBody AppointmentCreateRequest appointmentCreateRequest) {
		AppointmentCreateResponse response = appointmentManageService.create(userId, appointmentCreateRequest);
		FastApiResponse fastApiResponse = fastApiService.sendEntranceCodeAndUserIdToFastApi(response.entranceCode(),
			response.userId());

		return ResponseEntity.ok(fastApiResponse);
	}

	@PostMapping("/entrance")
	public ResponseEntity<FastApiResponse> create(@CurrentMember Long userId,
		@RequestBody AppointmentEnterRequest appointmentEnterRequest) {

		AppointmentEntranceResponse response = appointmentManageService.enter(userId, appointmentEnterRequest);
		FastApiResponse fastApiResponse = fastApiService.sendEntranceCodeAndUserIdToFastApi(response.entranceCode(), response.userId());

		return ResponseEntity.ok(fastApiResponse);
	}

	@GetMapping
	public ResponseEntity<List<AppointmentsGetResponse>> readAll(@CurrentMember Long userId) {
		List<AppointmentsGetResponse> result = appointmentManageService.readAll(userId);
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/{roomId}")
	public ResponseEntity<String> delete(@CurrentMember Long userId, @PathVariable Long roomId) {
		appointmentManageService.delete(userId, roomId);
		return ResponseEntity.ok("성공");
	}

	@GetMapping("/remaining-count/{roomId}")
	public ResponseEntity<AppointmentRemainingResponse> read(@CurrentMember Long userId, @PathVariable Long roomId) {
		AppointmentRemainingResponse response = appointmentManageService.read(userId, roomId);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/setting/{roomId}")
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

	@GetMapping("/all-board")
	public ResponseEntity<AllAppointmentBoardResponse> readAllRoom() {
		AllAppointmentBoardResponse response = appointmentManageService.readAll();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/report/{boardId}")
	public ResponseEntity<String> updateReportCount(
		@PathVariable Long boardId) {
		appointmentManageService.update(boardId);
		return ResponseEntity.ok(SuccessCode.UPDATE_SUCCESS.getMessage());
	}

	@PostMapping("/meeting")
	public ResponseEntity<Map<String, Object>> createAppointment(
		@CurrentMember Long userId,
		@RequestBody AppointmentExtractTimeRequest request
	) {
		FastApiResponse fastApiResponse = fastApiService.sendEntranceCodeToFastApi(request.entranceCode());
		Map<String, Object> result = appointmentManageService.processFastApiResponse(fastApiResponse.response(), userId, request.roomId());
		return ResponseEntity.ok(result);
	}
}
