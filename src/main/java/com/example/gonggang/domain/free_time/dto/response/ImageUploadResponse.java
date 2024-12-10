package com.example.gonggang.domain.free_time.dto.response;

public record ImageUploadResponse(
	String imageUrl,
	Long memberId
) {
	public static ImageUploadResponse of(String imageUrl, Long memberId) {
		return new ImageUploadResponse(imageUrl, memberId);
	}
}
