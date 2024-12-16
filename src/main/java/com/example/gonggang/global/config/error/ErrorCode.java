package com.example.gonggang.global.config.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	INVALID_EXTENSION_MESSAGE(400, "FILE_PROCESSING_FAIL", "이미지 확장자는 jpg, png, webp만 가능합니다."),
	IMAGE_SIZE_EXCEEDED_MESSAGE(400, "IMAGE_SIZE_EXCEEDED_MESSAGE", "이미지 사이즈는 5MB를 넘을 수 없습니다."),
	INVALID_REFRESH_TOKEN_ERROR(400, "INVALID_REFRESH_TOKEN_ERROR", "잘못된 리프레쉬 토큰입니다"),
	REFRESH_TOKEN_EMPTY_ERROR(400, "REFRESH_TOKEN_EMPTY_ERROR", "리프레쉬 토큰이 비어있습니다"),
	REFRESH_TOKEN_MEMBER_ID_MISMATCH_ERROR(400, "REFRESH_TOKEN_MEMBER_ID_MISMATCH_ERROR", "리프레쉬 토큰의 사용자 정보가 일치하지 않습니다"),
	REFRESH_TOKEN_SIGNATURE_ERROR(400, "REFRESH_TOKEN_SIGNATURE_ERROR", "리프레쉬 토큰의 서명의 잘못 되었습니다"),
	MAX_APPOINTMENT_PARTICIPANTS(400,"MAX_APPOINTMENT_PARTICIPANTS","약속 인원을 초과했습니다"),
	SOCIAL_TYPE_BAD_REQUEST(400, "SOCIAL_TYPE_BAD_REQUEST", "로그인 요청이 유효하지 않습니다."),
	UNSUPPORTED_REFRESH_TOKEN_ERROR(400, "UNSUPPORTED_REFRESH_TOKEN_ERROR", "지원하지 않는 리프레쉬 토큰입니다"),
	ALREADY_ENTERED(400,"ALREADY_ENTERED","이미 참여하고 있는 공강팟입니다"),
	INVALID_WEEKDAY_PROVIDED(400, "INVALID_WEEKDAY_PROVIDED", "WeekDay 열거형에 정의되지 않은 값입니다."),
	INVALID_OUTPUT_LIST(400, "INVALID_OUTPUT_LIST", "출력값이 리스트 형식이 아닙니다."),
	IO_EXCEPTION(400, "IO_EXCEPTION", "입출력 예외가 발생했습니다."),
	EMPTY_OUTPUT_LIST(400, "EMPTY_OUTPUT_LIST", "출력값이 비어있는 리스트입니다."),
	INVALID_OUTPUT_FIRST_ELEMENT(400, "INVALID_OUTPUT_FIRST_ELEMENT", "출력값의 첫 번째 요소가 Map 형식이 아닙니다."),
	OVER_VALUE(400, "OVER_VALUE", "현재 참여하고 있는 인원 수 보다 적게 설정할 수 없습니다"),

	/* 401 */
	AUTHENTICATION_CODE_EXPIRED(401, "AUTHENTICATION_CODE_EXPIRED", "인가코드가 만료되었습니다"),
	REFRESH_TOKEN_EXPIRED_ERROR(401, "REFRESH_TOKEN_EXPIRED_ERROR", "리프레쉬 토큰이 만료되었습니다"),

	/* 403 */
	PASSWORD_NOT_MATCH(403, "PASSWORD_NOT_MATCH", "비밀번호가 일치하지 않습니다."),
	ROLE_FORBIDDEN(403, "ROLE_FORBIDDEN", "액세스할 수 있는 권한이 아닙니다."),

	/* 404 */
	MEMBER_NOT_FOUND(404, "MEMBER_NOT_FOUND", "회원이 없습니다."),
	APPOINTMENT_NOT_FOUND(404, "APPOINTMENT_NOT_FOUND", "약속을 찾을 수 없습니다"),
	USER_NOT_FOUND(404,"USER_NOT_FOUND","존재하지 않는 유저입니다."),
	ADMIN_NOT_FOUND(404, "ADMIN_NOT_FOUND", "존재하지 않는 관리자입니다."),
	REFRESH_TOKEN_NOT_FOUND(404, "REFRESH_TOKEN_NOT_FOUND", "리프레쉬 토큰이 존재하지 않습니다"),
	PARTICIPANT_NOT_FOUND(404,"PARTICIPANT_NOT_FOUND","참가자가 존재하지 않습니다"),
	BOARD_NOT_FOUND(404,"BOARD_NOT_FOUND","채팅방이 존재하지 않습니다"),

	/* 500 */
	INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."),
	UNKNOWN_REFRESH_TOKEN_ERROR(500, "UNKNOWN_REFRESH_TOKEN_ERROR", "알 수 없는 리프레쉬 토큰 오류가 발생했습니다"),
	S3_UPLOAD_FAIL(500, "S3_UPLOAD_FAIL", "S3 업로드 중 오류가 발생했습니다.");


	private final Integer httpStatus;
	private final String code;
	private final String message;
}
