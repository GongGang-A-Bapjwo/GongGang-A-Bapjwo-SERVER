package com.example.gonggang.global.auth.jwt.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class InvalidRefreshTokenException extends ServiceException {
    public InvalidRefreshTokenException() {
        super(ErrorCode.INVALID_REFRESH_TOKEN_ERROR);
    }
}
