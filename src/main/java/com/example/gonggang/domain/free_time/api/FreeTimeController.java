package com.example.gonggang.domain.free_time.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.gonggang.domain.free_time.application.FreeTimeImageService;
import com.example.gonggang.domain.free_time.dto.response.ImageUploadResponse;
import com.example.gonggang.global.auth.annotation.CurrentMember;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/free-time")
@RequiredArgsConstructor
public class FreeTimeController implements FreeTimeApi {
	private final FreeTimeImageService freeTimeImageService;

	@Override
	@PostMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ImageUploadResponse> uploadFreeTimeImage(
		@CurrentMember Long memberId,
		@RequestPart("file") MultipartFile file
	) {
		ImageUploadResponse response = freeTimeImageService.uploadImage(memberId, file);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
