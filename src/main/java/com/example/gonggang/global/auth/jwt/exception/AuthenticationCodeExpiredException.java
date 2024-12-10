package com.example.gonggang.global.auth.jwt.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class AuthenticationCodeExpiredException extends ServiceException {
    public AuthenticationCodeExpiredException() {
        super(ErrorCode.AUTHENTICATION_CODE_EXPIRED);
    }
}
