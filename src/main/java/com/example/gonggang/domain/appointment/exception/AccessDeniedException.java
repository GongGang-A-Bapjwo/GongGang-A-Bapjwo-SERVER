package com.example.gonggang.domain.appointment.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class AccessDeniedException extends ServiceException {
    public AccessDeniedException() {
        super(ErrorCode.ROLE_FORBIDDEN);
    }
}
