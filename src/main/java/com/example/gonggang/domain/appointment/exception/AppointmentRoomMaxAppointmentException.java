package com.example.gonggang.domain.appointment.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class AppointmentRoomMaxAppointmentException extends ServiceException {
    public AppointmentRoomMaxAppointmentException() {
        super(ErrorCode.MAX_APPOINTMENT_PARTICIPANTS);
    }
}
