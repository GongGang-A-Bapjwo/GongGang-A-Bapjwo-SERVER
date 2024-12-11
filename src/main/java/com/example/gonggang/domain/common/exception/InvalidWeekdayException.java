package com.example.gonggang.domain.common.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class InvalidWeekdayException extends ServiceException {
	public InvalidWeekdayException() {
		super(ErrorCode.INVALID_WEEKDAY_PROVIDED);
	}
}
