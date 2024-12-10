package com.example.gonggang.domain.external.s3.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class FileProcessingFailException extends ServiceException {
    public FileProcessingFailException() {
        super(ErrorCode.FILE_PROCESSING_FAIL);
    }
}
