package com.example.gonggang.domain.external.fast_api.application;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.gonggang.domain.external.fast_api.feign.FastApiClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FastApiService {
	private final FastApiClient fastApiClient;

	public Map<String, Object> sendImageUrlAndMemberIdToFastApi(String imageUrl, String memberId) {
		return fastApiClient.sendImageUrlAndMemberId(imageUrl, memberId);
	}
}
