package com.example.gonggang.global.config.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    ADMIN_REGISTER_SUCCESS(201, "ADMIN_REGISTER_SUCCESS", "관리자 등록 성공"),
    SIGN_OUT_SUCCESS(200, "SIGN_OUT_SUCCESS","로그아웃 성공");

    private final int httpStatus;
    private final String code;
    private final String message;
}
