package com.example.gonggang.global.config.success;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    ;
    private final int httpStatus;
    private final String code;
    private final String message;
}
