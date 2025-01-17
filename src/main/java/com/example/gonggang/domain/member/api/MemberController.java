package com.example.gonggang.domain.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gonggang.domain.member.application.AuthenticationService;
import com.example.gonggang.domain.member.application.SocialLoginService;
import com.example.gonggang.domain.member.dto.AccessTokenGetSuccess;
import com.example.gonggang.domain.member.dto.LoginSuccessResponse;
import com.example.gonggang.global.auth.annotation.CurrentMember;
import com.example.gonggang.global.auth.client.dto.MemberLoginRequest;
import com.example.gonggang.global.auth.jwt.application.TokenService;
import com.example.gonggang.global.config.success.SuccessCode;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController implements MemberApi {

	private final TokenService tokenService;
	private final AuthenticationService authenticationService;
	private final SocialLoginService socialLoginService;

	@Override
	@PostMapping("/sign-up")
	public ResponseEntity<LoginSuccessResponse> signUp(
		@RequestParam final String authorizationCode,
		@RequestBody final MemberLoginRequest loginRequest,
		HttpServletResponse response
	) {
		LoginSuccessResponse loginSuccessResponse = socialLoginService.handleSocialLogin(authorizationCode,
			loginRequest);
		return ResponseEntity.ok().body(loginSuccessResponse);
	}

	@Override
	@GetMapping("/refresh-token")
	public ResponseEntity<AccessTokenGetSuccess> issueAccessTokenUsingRefreshToken(
		@RequestHeader("Authorization_Refresh") final String refreshToken
	) {
		AccessTokenGetSuccess accessTokenGetSuccess = authenticationService.generateAccessTokenFromRefreshToken(
			refreshToken);
		return ResponseEntity.ok().body(accessTokenGetSuccess);
	}

	@Override
	@PostMapping("/sign-out")
	public ResponseEntity<String> signOut(
		@CurrentMember final Long memberId
	) {
		tokenService.deleteRefreshToken(memberId);
		return ResponseEntity.ok().body(SuccessCode.SIGN_OUT_SUCCESS.getMessage());
	}
}
