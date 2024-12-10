package com.example.gonggang.domain.free_time.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.gonggang.domain.free_time.dto.response.ImageUploadResponse;
import com.example.gonggang.global.auth.annotation.CurrentMember;
import com.example.gonggang.global.config.error.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "FreeTime", description = "공강 관련 API")
public interface FreeTimeApi {

	@Operation(summary = "유저의 이미지 업로드", description = "사용자가 자신의 이미지를 업로드하는 API입니다. 이미지는 S3에 저장되고, 해당 이미지 URL이 반환됩니다.")
	@ApiResponses(
		value = {
			@ApiResponse(
				responseCode = "201",
				description = "이미지 업로드 성공"
			),
			@ApiResponse(
				responseCode = "400",
				description = "이미지 확장자는 jpg, png, webp만 가능합니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "400",
				description = "이미지 사이즈는 5MB를 넘을 수 없습니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "401",
				description = "회원이 없습니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "500",
				description = "S3 업로드 중 오류가 발생했습니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	ResponseEntity<ImageUploadResponse> uploadFreeTimeImage(
		@CurrentMember Long memberId,
		@RequestPart("file") MultipartFile file
	);
}
