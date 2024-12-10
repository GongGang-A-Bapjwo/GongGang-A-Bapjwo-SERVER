package com.example.gonggang.global.auth.jwt.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class UnknownRefreshTokenException extends ServiceException {
    public UnknownRefreshTokenException() {
        super(ErrorCode.UNKNOWN_REFRESH_TOKEN_ERROR);
    }
}
