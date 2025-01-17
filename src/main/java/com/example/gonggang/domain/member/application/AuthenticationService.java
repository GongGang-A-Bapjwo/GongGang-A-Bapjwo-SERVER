package com.example.gonggang.domain.member.application;

import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gonggang.domain.free_time.repository.FreeTimeRepository;
import com.example.gonggang.domain.member.dto.AccessTokenGetSuccess;
import com.example.gonggang.domain.member.dto.LoginSuccessResponse;
import com.example.gonggang.domain.users.domain.Role;
import com.example.gonggang.domain.users.domain.Users;
import com.example.gonggang.global.auth.client.dto.MemberInfoResponse;
import com.example.gonggang.global.auth.jwt.application.TokenService;
import com.example.gonggang.global.auth.jwt.exception.InvalidRefreshTokenException;
import com.example.gonggang.global.auth.jwt.exception.RefreshTokenEmptyException;
import com.example.gonggang.global.auth.jwt.exception.RefreshTokenExpiredException;
import com.example.gonggang.global.auth.jwt.exception.RefreshTokenMemberIdMismatchException;
import com.example.gonggang.global.auth.jwt.exception.RefreshTokenSignatureException;
import com.example.gonggang.global.auth.jwt.exception.UnknownRefreshTokenException;
import com.example.gonggang.global.auth.jwt.exception.UnsupportedRefreshTokenException;
import com.example.gonggang.global.auth.jwt.provider.JwtTokenProvider;
import com.example.gonggang.global.auth.jwt.provider.JwtValidationType;
import com.example.gonggang.global.auth.security.AdminAuthentication;
import com.example.gonggang.global.auth.security.MemberAuthentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private static final String BEARER_PREFIX = "Bearer ";
	private final FreeTimeRepository freeTimeRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final TokenService tokenService;

	/**
	 * 사용자의 로그인 성공 시 Access Token과 Refresh Token을 생성하고,
	 * 로그인 성공 응답 객체(LoginSuccessResponse)를 반환하는 메서드.
	 *
	 * @param memberId 회원의 고유 ID
	 * @param user 로그인한 사용자의 정보가 포함된 Users 객체
	 * @param memberInfoResponse 로그인 시 외부로부터 전달된 회원 정보
	 * @return 로그인 성공 응답(LoginSuccessResponse)
	 */
	public LoginSuccessResponse generateLoginSuccessResponse(final Long memberId, final Users user,
		final MemberInfoResponse memberInfoResponse) {
		String nickname = memberInfoResponse.nickname();
		Role role = user.getRole();
		Collection<GrantedAuthority> authorities = List.of(role.toGrantedAuthority());

		log.info("Starting login success response generation for memberId: {}, nickname: {}, role: {}", memberId,
			nickname, role.getRoleName());

		UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationToken(memberId, role,
			authorities);
		String refreshToken = issueAndSaveRefreshToken(memberId, authenticationToken);
		String accessToken = jwtTokenProvider.issueAccessToken(authenticationToken);

		log.info("Login success for authorities: {}, accessToken: {}, refreshToken: {}", authorities, accessToken,
			refreshToken);

		return LoginSuccessResponse.of(accessToken, refreshToken, nickname, role.getRoleName(),
			!freeTimeRepository.findAllByUser(user).isEmpty());
	}

	/**
	 * Refresh Token을 사용하여 새로운 Access Token을 생성하는 메서드.
	 *
	 * Refresh Token에서 사용자 ID와 Role 정보를 추출한 후,
	 * Role에 따라 Admin 또는 Member 권한으로 새로운 Access Token을 발급합니다.
	 *
	 * @param refreshTokenWithBearer "Bearer +  사용자의 Refresh Token"
	 * @return 새로운 Access Token 정보가 포함된 AccessTokenGetSuccess 객체
	 */
	@Transactional
	public AccessTokenGetSuccess generateAccessTokenFromRefreshToken(final String refreshTokenWithBearer) {
		String refreshToken = refreshTokenWithBearer;
		if (refreshToken.startsWith(BEARER_PREFIX)) {
			refreshToken = refreshToken.substring(BEARER_PREFIX.length());
		}

		log.info("Validation result for refresh token: {}", jwtTokenProvider.validateToken(refreshToken));

		JwtValidationType validationType = jwtTokenProvider.validateToken(refreshToken);
		if (!validationType.equals(JwtValidationType.VALID_JWT)) {
			log.warn("Invalid refresh token: {}", validationType);
			throw switch (validationType) {
				case EXPIRED_JWT_TOKEN -> new RefreshTokenExpiredException();
				case INVALID_JWT_TOKEN -> new InvalidRefreshTokenException();
				case INVALID_JWT_SIGNATURE -> new RefreshTokenSignatureException();
				case UNSUPPORTED_JWT_TOKEN -> new UnsupportedRefreshTokenException();
				case EMPTY_JWT -> new RefreshTokenEmptyException();
				default -> new UnknownRefreshTokenException();
			};
		}

		Long memberId = jwtTokenProvider.getMemberIdFromJwt(refreshToken);

		if (!memberId.equals(tokenService.findIdByRefreshToken(refreshToken))) {
			log.error("MemberId mismatch: token does not match the stored refresh token");
			throw new RefreshTokenMemberIdMismatchException();
		}

		Role role = jwtTokenProvider.getRoleFromJwt(refreshToken);
		Collection<GrantedAuthority> authorities = List.of(role.toGrantedAuthority());

		UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationToken(memberId, role,
			authorities);
		log.info("Generated new access token for memberId: {}, role: {}, authorities: {}",
			memberId, role.getRoleName(), authorities);
		return AccessTokenGetSuccess.of(jwtTokenProvider.issueAccessToken(authenticationToken));
	}

	/**
	 * Refresh Token을 발급하고 저장하는 메서드.
	 * 발급된 Refresh Token을 TokenService에 저장
	 *
	 * @param memberId 회원의 고유 ID
	 * @param authenticationToken 사용자 인증 정보
	 * @return 발급된 Refresh Token
	 */
	private String issueAndSaveRefreshToken(Long memberId, UsernamePasswordAuthenticationToken authenticationToken) {
		String refreshToken = jwtTokenProvider.issueRefreshToken(authenticationToken);
		log.info("Issued new refresh token for memberId: {}", memberId);
		tokenService.saveRefreshToken(memberId, refreshToken);
		return refreshToken;
	}

	/**
	 * 사용자 Role에 따라 적절한 Authentication 객체(Admin 또는 Member)를 생성하는 메서드.
	 *
	 * @param memberId 회원의 고유 ID
	 * @param role 사용자 Role (ADMIN 또는 MEMBER)
	 * @param authorities 사용자에게 부여된 권한 목록
	 * @return 생성된 Admin 또는 Member Authentication 객체
	 */
	private UsernamePasswordAuthenticationToken createAuthenticationToken(Long memberId, Role role,
		Collection<GrantedAuthority> authorities) {
		if (role == Role.ADMIN) {
			log.info("Creating AdminAuthentication for memberId: {}", memberId);
			return new AdminAuthentication(memberId, null, authorities);
		} else {
			log.info("Creating MemberAuthentication for memberId: {}", memberId);
			return new MemberAuthentication(memberId, null, authorities);
		}
	}
}
