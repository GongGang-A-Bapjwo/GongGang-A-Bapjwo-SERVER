package com.example.gonggang.domain.free_time.api;

import com.example.gonggang.domain.free_time.application.FreeTimeManageService;
import com.example.gonggang.domain.free_time.dto.request.FreeTimeRequest;
import com.example.gonggang.domain.free_time.dto.response.FreeTimeAllResponse;
import com.example.gonggang.global.config.success.SuccessCode;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.gonggang.domain.external.fast_api.application.FastApiService;
import com.example.gonggang.domain.free_time.application.FreeTimeImageService;
import com.example.gonggang.domain.free_time.application.FreeTimeSaveService;
import com.example.gonggang.domain.free_time.dto.response.FreeTimeResponse;
import com.example.gonggang.domain.free_time.dto.response.ImageUploadResponse;
import com.example.gonggang.global.auth.annotation.CurrentMember;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/free-time")
@RequiredArgsConstructor
public class FreeTimeController implements FreeTimeApi {

	private final FreeTimeImageService freeTimeImageService;
	private final FastApiService fastApiService;
	private final FreeTimeSaveService freeTimeSaveService;
	private final FreeTimeManageService freeTimeManageService;

	@Override
	@PostMapping(value = "/process-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<List<FreeTimeResponse>> processFreeTimeImage(
		@CurrentMember Long memberId,
		@RequestPart("file") MultipartFile file
	) {
		ImageUploadResponse response = freeTimeImageService.uploadImage(memberId, file);

		Map<String, Object> fastApiResponse = fastApiService.sendImageUrlAndMemberIdToFastApi(
			response.imageUrl(), response.memberId().toString()
		);

		List<FreeTimeResponse> freeTimes = freeTimeSaveService.saveFreeTimes(fastApiResponse);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(freeTimes);
	}

	@PostMapping("/set-freetime")
	public ResponseEntity<String> create(@CurrentMember Long userId, @RequestBody FreeTimeRequest freeTimeRequest) {
		freeTimeSaveService.create(userId, freeTimeRequest);
		return ResponseEntity.ok(SuccessCode.CREATE_SUCCESS.getMessage());
	}

	@GetMapping("/info")
	public ResponseEntity<List<FreeTimeAllResponse>>	 read(@CurrentMember Long userId) {
		List<FreeTimeAllResponse> response = freeTimeManageService.readAll(userId);
		return ResponseEntity.ok(response);
	}
}
