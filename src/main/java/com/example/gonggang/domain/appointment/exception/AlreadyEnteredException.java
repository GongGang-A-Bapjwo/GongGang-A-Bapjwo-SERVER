package com.example.gonggang.domain.appointment.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class AlreadyEnteredException extends ServiceException {
    public AlreadyEnteredException() {
        super(ErrorCode.ALREADY_ENTERED);
    }
}
