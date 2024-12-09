package com.example.gonggang.global.config.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	FILE_PROCESSING_FAIL(400, "FILE_PROCESSING_FAIL", "파일 처리 중 오류가 발생했습니다."),
	INVALID_REFRESH_TOKEN_ERROR(400, "INVALID_REFRESH_TOKEN_ERROR", "잘못된 리프레쉬 토큰입니다"),
	REFRESH_TOKEN_EMPTY_ERROR(400, "REFRESH_TOKEN_EMPTY_ERROR", "리프레쉬 토큰이 비어있습니다"),
	REFRESH_TOKEN_MEMBER_ID_MISMATCH_ERROR(400, "REFRESH_TOKEN_MEMBER_ID_MISMATCH_ERROR", "리프레쉬 토큰의 사용자 정보가 일치하지 않습니다"),
	REFRESH_TOKEN_SIGNATURE_ERROR(400, "REFRESH_TOKEN_SIGNATURE_ERROR", "리프레쉬 토큰의 서명의 잘못 되었습니다"),
	S3_UPLOAD_FAIL(400, "S3_UPLOAD_FAIL", "S3 업로드 중 오류가 발생했습니다."),
	SOCIAL_TYPE_BAD_REQUEST(400, "SOCIAL_TYPE_BAD_REQUEST", "로그인 요청이 유효하지 않습니다."),
	UNSUPPORTED_REFRESH_TOKEN_ERROR(400, "UNSUPPORTED_REFRESH_TOKEN_ERROR", "지원하지 않는 리프레쉬 토큰입니다"),
	AUTHENTICATION_CODE_EXPIRED(401, "AUTHENTICATION_CODE_EXPIRED", "인가코드가 만료되었습니다"),
	REFRESH_TOKEN_EXPIRED_ERROR(401, "REFRESH_TOKEN_EXPIRED_ERROR", "리프레쉬 토큰이 만료되었습니다"),
	PASSWORD_NOT_MATCH(403, "PASSWORD_NOT_MATCH", "비밀번호가 일치하지 않습니다."),
	ROLE_FORBIDDEN(403, "ROLE_FORBIDDEN", "액세스할 수 있는 권한이 아닙니다."),
	MEMBER_NOT_FOUND(404, "MEMBER_NOT_FOUND", "회원이 없습니다"),
	ADMIN_NOT_FOUND(404, "ADMIN_NOT_FOUND", "존재하지 않는 관리자입니다."),
	REFRESH_TOKEN_NOT_FOUND(404, "REFRESH_TOKEN_NOT_FOUND", "리프레쉬 토큰이 존재하지 않습니다"),
	INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."),
	UNKNOWN_REFRESH_TOKEN_ERROR(500, "UNKNOWN_REFRESH_TOKEN_ERROR", "알 수 없는 리프레쉬 토큰 오류가 발생했습니다");
	private final Integer httpStatus;
	private final String code;
	private final String message;
}
