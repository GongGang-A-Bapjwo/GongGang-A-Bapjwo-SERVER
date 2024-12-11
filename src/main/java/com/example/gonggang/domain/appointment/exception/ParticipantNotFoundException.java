package com.example.gonggang.domain.appointment.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class ParticipantNotFoundException extends ServiceException {
    public ParticipantNotFoundException() {
        super(ErrorCode.PARTICIPANT_NOT_FOUND);
    }
}
