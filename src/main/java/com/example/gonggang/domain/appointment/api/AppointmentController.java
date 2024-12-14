package com.example.gonggang.domain.appointment.api;

import io.swagger.v3.oas.annotations.Operation;
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

	@Operation(summary = "공강팟 만들기 API", description = "공강팟을 만드는 POST API입니다")
	@PostMapping
	public ResponseEntity<FastApiResponse> create(@CurrentMember Long userId,
		@RequestBody AppointmentCreateRequest appointmentCreateRequest) {
		AppointmentCreateResponse response = appointmentManageService.create(userId, appointmentCreateRequest);
		FastApiResponse fastApiResponse = fastApiService.sendEntranceCodeAndUserIdToFastApi(response.entranceCode(),
			response.userId());

		return ResponseEntity.ok(fastApiResponse);
	}

	@Operation(summary = "공강팟 참여 API", description = "공강팟에 참여하는 POST API입니다.")
	@PostMapping("/entrance")
	public ResponseEntity<FastApiResponse> create(@CurrentMember Long userId,
		@RequestBody AppointmentEnterRequest appointmentEnterRequest) {

		AppointmentEntranceResponse response = appointmentManageService.enter(userId, appointmentEnterRequest);
		FastApiResponse fastApiResponse = fastApiService.sendEntranceCodeAndUserIdToFastApi(response.entranceCode(), response.userId());

		return ResponseEntity.ok(fastApiResponse);
	}

	@Operation(summary = "참여중인 공강팟 조회 API", description = "현재 자신이 참여하고 있는 모든 공강팟을 조회하는 API입니다.")
	@GetMapping
	public ResponseEntity<List<AppointmentsGetResponse>> readAll(@CurrentMember Long userId) {
		List<AppointmentsGetResponse> result = appointmentManageService.readAll(userId);
		return ResponseEntity.ok(result);
	}

	@Operation(summary = "공강팟 탈퇴 API", description = "참여중인 공강팟에서 탈퇴하는 API입니다. 공팟장인 경우 공강팟 자체를 삭제합니다.")
	@DeleteMapping("/{roomId}")
	public ResponseEntity<String> delete(@CurrentMember Long userId, @PathVariable Long roomId) {
		appointmentManageService.delete(userId, roomId);
		return ResponseEntity.ok("성공");
	}

	@Operation(summary = "공강팟 인원조회 API", description = "현재 공강팟의 참여인원, 최대인원을 반환하는 API입니다.")
	@GetMapping("/remaining-count/{roomId}")
	public ResponseEntity<AppointmentRemainingResponse> read(@CurrentMember Long userId, @PathVariable Long roomId) {
		AppointmentRemainingResponse response = appointmentManageService.read(userId, roomId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "공강팟 설정 변경 API", description = "공강팟의 설정을 변경하는 API입니다.")
	@PutMapping("/setting/{roomId}")
	public ResponseEntity<String> update(@CurrentMember Long userId, @PathVariable Long roomId,
		@RequestBody AppointmentCreateRequest request) {
		appointmentManageService.update(userId, roomId, request);
		return ResponseEntity.ok(SuccessCode.UPDATE_SUCCESS.getMessage());
	}

	@Operation(summary = "공강, 채팅방 조회 API ", description = "메인화면에서 필요한 자신의 오늘 공강시간과 참여가능한 채팅방을 조회하는 API입니다.")
	@GetMapping("/all")
	public ResponseEntity<AppointmentAllResponse> read(@CurrentMember Long userId) {
		AppointmentAllResponse response = appointmentManageService.read(userId);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "모든 채팅방 조회 API", description = "자신의 공강시간이 아니더라도 모든 참여가능한 채팅방을 조회하는 API입니다.")
	@GetMapping("/all-board")
	public ResponseEntity<AllAppointmentBoardResponse> readAllRoom() {
		AllAppointmentBoardResponse response = appointmentManageService.readAll();
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "공강팟 신고기능 API", description = "공강팟을 신고하는 API입니다.")
	@GetMapping("/report/{boardId}")
	public ResponseEntity<String> updateReportCount(
		@PathVariable Long boardId) {
		appointmentManageService.update(boardId);
		return ResponseEntity.ok(SuccessCode.UPDATE_SUCCESS.getMessage());
	}

	@Operation(summary = "시간 잡기 API", description = "공강팟의 시간잡기를 위한 API입니다.")
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
