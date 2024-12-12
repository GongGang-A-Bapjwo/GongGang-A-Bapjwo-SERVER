package com.example.gonggang.domain.free_time.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class OutputListIsEmptyException extends ServiceException {
	public OutputListIsEmptyException() {
		super(ErrorCode.EMPTY_OUTPUT_LIST);
	}
}
