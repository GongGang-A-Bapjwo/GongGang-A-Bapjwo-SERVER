package com.example.gonggang.global.auth.jwt.application;

import org.springframework.stereotype.Service;

import com.example.gonggang.global.auth.jwt.exception.RefreshTokenNotFoundException;
import com.example.gonggang.global.auth.jwt.repository.TokenRepository;
import com.example.gonggang.global.auth.redis.Token;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

	private final TokenRepository tokenRepository;

	@Transactional
	public void saveRefreshToken(final Long memberId, final String refreshToken) {
		tokenRepository.save(Token.of(memberId, refreshToken));
	}

	public Long findIdByRefreshToken(final String refreshToken) {
		Token token = tokenRepository.findByRefreshToken(refreshToken)
			.orElseThrow(RefreshTokenNotFoundException::new);

		return token.getId();
	}

	@Transactional
	public void deleteRefreshToken(final Long memberId) {
		Token token = tokenRepository.findById(memberId).orElseThrow(RefreshTokenNotFoundException::new);
		tokenRepository.delete(token);
		log.info("Deleted refresh token: {}", token);
	}
}
