package com.example.gonggang.admin.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class PasswordNotMatchException extends ServiceException {
	public PasswordNotMatchException() {
		super(ErrorCode.PASSWORD_NOT_MATCH);
	}
}
