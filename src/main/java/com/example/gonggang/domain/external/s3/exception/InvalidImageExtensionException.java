package com.example.gonggang.domain.external.s3.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class InvalidImageExtensionException extends ServiceException {
	public InvalidImageExtensionException() {
		super(ErrorCode.INVALID_EXTENSION_MESSAGE);
	}
}
