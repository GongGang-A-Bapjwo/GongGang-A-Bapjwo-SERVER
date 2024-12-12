package com.example.gonggang.domain.appointment.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class OverThanCurrentUserException extends ServiceException {
    public OverThanCurrentUserException() {
        super(ErrorCode.OVER_VALUE);
    }
}
