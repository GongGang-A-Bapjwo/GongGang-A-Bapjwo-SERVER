package com.example.gonggang.global.config.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."),
	ROLE_FORBIDDEN(403, "ROLE_FORBIDDEN", "액세스할 수 있는 권한이 아닙니다."),
	S3_UPLOAD_FAIL(400, "S3_UPLOAD_FAIL", "S3 업로드 중 오류가 발생했습니다."),
	FILE_PROCESSING_FAIL(400, "FILE_PROCESSING_FAIL", "파일 처리 중 오류가 발생했습니다.");

	private final Integer httpStatus;
	private final String code;
	private final String message;
}
