package com.example.gonggang.domain.free_time.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class OutputIsNotListException extends ServiceException {
	public OutputIsNotListException() {
		super(ErrorCode.INVALID_OUTPUT_LIST);
	}
}
