package com.example.gonggang.domain.appointment.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class AppointmentRoomNotFoundException extends ServiceException {
    public AppointmentRoomNotFoundException() {
        super(ErrorCode.APPOINTMENT_NOT_FOUND);
    }
}
