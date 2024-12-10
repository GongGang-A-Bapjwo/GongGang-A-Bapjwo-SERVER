package com.example.gonggang.global.auth.jwt.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class RefreshTokenEmptyException extends ServiceException {
    public RefreshTokenEmptyException() {
        super(ErrorCode.REFRESH_TOKEN_EMPTY_ERROR);
    }
}
