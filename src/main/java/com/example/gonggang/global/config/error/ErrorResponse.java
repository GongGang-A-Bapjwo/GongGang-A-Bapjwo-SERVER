package com.example.gonggang.global.config.error;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final int httpStatus;
    private final String message;
    private final String code;

    private ErrorResponse(ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }
}