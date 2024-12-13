package com.example.gonggang.domain.appointment.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class AppointmentBoardNotFoundException extends ServiceException {
    public AppointmentBoardNotFoundException() {
        super(ErrorCode.BOARD_NOT_FOUND);
    }
}
