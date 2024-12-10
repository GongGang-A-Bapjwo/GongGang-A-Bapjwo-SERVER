package com.example.gonggang.global.auth.jwt.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class RefreshTokenSignatureException extends ServiceException {
    public RefreshTokenSignatureException() {
        super(ErrorCode.REFRESH_TOKEN_SIGNATURE_ERROR);
    }
}
