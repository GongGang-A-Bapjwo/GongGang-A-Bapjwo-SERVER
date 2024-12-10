package com.example.gonggang.global.auth.jwt.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class UnsupportedRefreshTokenException extends ServiceException {
    public UnsupportedRefreshTokenException() {
        super(ErrorCode.UNSUPPORTED_REFRESH_TOKEN_ERROR);
    }
}
