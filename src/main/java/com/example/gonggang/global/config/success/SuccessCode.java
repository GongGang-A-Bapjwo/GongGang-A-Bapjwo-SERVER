package com.example.gonggang.global.config.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    ADMIN_REGISTER_SUCCESS(201, "ADMIN_REGISTER_SUCCESS", "관리자 등록 성공"),
    SIGN_OUT_SUCCESS(200, "SIGN_OUT_SUCCESS","로그아웃 성공"),
    ENTER_SUCCESS(200,"ENTER_SUCCESSED","공강팟 입장 성공"),
    UPDATE_SUCCESS(200, "UPDATE_SUCCESS", "업데이트 성공"),
    CREATE_SUCCESS(200,"CREATE_SUCCESS","생성 성공"),
    DELETE_SUCCESS(200,"DELETE_SUCCESS", "삭제 성공");

    private final int httpStatus;
    private final String code;
    private final String message;
}
