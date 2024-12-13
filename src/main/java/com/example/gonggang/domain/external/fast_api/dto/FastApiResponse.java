package com.example.gonggang.domain.external.fast_api.dto;

import java.util.Map;

public record FastApiResponse(
	Map<String, Object> response
) {
	public static FastApiResponse from(Map<String, Object> response) {
		return new FastApiResponse(response);
	}
}
