package com.example.gonggang.domain.free_time.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.gonggang.domain.free_time.dto.response.FreeTimeResponse;
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

	@Operation(summary = "이미지를 처리하고 공강 시간을 반환 및 저장하는 POST API", description = "사용자가 자신의 이미지를 업로드하면 해당 이미지에서 공강 시간을 추출해서 저장 및 반환하는 API 입니다.")
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
	ResponseEntity<List<FreeTimeResponse>> processFreeTimeImage(
		@CurrentMember Long memberId,
		@RequestPart("file") MultipartFile file
	);
}
