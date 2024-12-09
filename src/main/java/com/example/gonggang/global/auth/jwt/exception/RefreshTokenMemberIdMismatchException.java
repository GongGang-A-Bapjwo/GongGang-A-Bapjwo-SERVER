package com.example.gonggang.global.auth.jwt.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class RefreshTokenMemberIdMismatchException extends ServiceException {
    public RefreshTokenMemberIdMismatchException() {
        super(ErrorCode.REFRESH_TOKEN_MEMBER_ID_MISMATCH_ERROR);
    }
}
