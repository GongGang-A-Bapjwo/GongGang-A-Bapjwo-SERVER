package com.example.gonggang.domain.external.fast_api.application;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.gonggang.domain.external.fast_api.dto.FastApiResponse;
import com.example.gonggang.domain.external.fast_api.feign.FastApiClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FastApiService {
	private final FastApiClient fastApiClient;

	public FastApiResponse sendImageUrlAndMemberIdToFastApi(String imageUrl, String memberId) {
		Map<String, Object> response = fastApiClient.sendImageUrlAndMemberId(imageUrl, memberId);
		return FastApiResponse.from(response);
	}

	public FastApiResponse sendEntranceCodeAndUserIdToFastApi(String entranceCode, Long userId) {
		Map<String, Object> response = fastApiClient.sendEntranceCodeAndUserId(entranceCode, userId.intValue());
		return FastApiResponse.from(response);
	}
}
