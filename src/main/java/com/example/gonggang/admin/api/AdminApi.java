package com.example.gonggang.admin.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.gonggang.admin.application.dto.request.AdminLoginRequest;
import com.example.gonggang.admin.application.dto.request.AdminRegisterRequest;
import com.example.gonggang.domain.member.dto.LoginSuccessResponse;
import com.example.gonggang.global.config.error.ErrorResponse;
import com.example.gonggang.global.config.swagger.annotation.DisableSwaggerSecurity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "Admin", description = "관리자 제어 API")
public interface AdminApi {

	@DisableSwaggerSecurity
	@Operation(summary = "관리자 테스트 회원가입", description = "테스트를 위한 관리자 회원가입 기능입니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			description = "관리자 회원가입에 성공하였습니다."
		),
		@ApiResponse(
			responseCode = "500",
			description = "관리자 회원가입에 실패하였습니다.",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@PostMapping("/test-sign-up")
	ResponseEntity<String> register(@RequestBody AdminRegisterRequest request);

	@Operation(summary = "관리자 로그인", description = "관리자 계정으로 로그인합니다.")
	@ApiResponses(
		value = {
			@ApiResponse(
				responseCode = "200",
				description = "관리자 로그인 성공"
			),
			@ApiResponse(
				responseCode = "400",
				description = "로그인 요청이 유효하지 않습니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
				responseCode = "404",
				description = "관리자 정보를 찾을 수 없습니다.",
				content = @Content(schema = @Schema(implementation = ErrorResponse.class))
			)
		}
	)
	@PostMapping("/login")
	ResponseEntity<LoginSuccessResponse> login(@RequestBody AdminLoginRequest request, HttpServletResponse response);
}
