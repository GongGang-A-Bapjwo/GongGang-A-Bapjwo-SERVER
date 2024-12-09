package com.example.gonggang.global.auth.jwt.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class RefreshTokenExpiredException extends ServiceException {
    public RefreshTokenExpiredException() {
        super(ErrorCode.REFRESH_TOKEN_EXPIRED_ERROR);
    }
}
