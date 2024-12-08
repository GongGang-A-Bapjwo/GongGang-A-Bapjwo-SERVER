package com.example.gonggang.global.config.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    ;
    private final int httpStatus;
    private final String code;
    private final String message;
}
