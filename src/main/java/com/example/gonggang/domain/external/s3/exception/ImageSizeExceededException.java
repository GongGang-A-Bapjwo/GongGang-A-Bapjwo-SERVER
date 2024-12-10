package com.example.gonggang.domain.external.s3.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class ImageSizeExceededException extends ServiceException {
	public ImageSizeExceededException() {
		super(ErrorCode.IMAGE_SIZE_EXCEEDED_MESSAGE);
	}
}
