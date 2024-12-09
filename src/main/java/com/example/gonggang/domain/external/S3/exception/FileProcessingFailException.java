package com.example.gonggang.domain.external.S3.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class FileProcessingFailException extends ServiceException {
    public FileProcessingFailException() {
        super(ErrorCode.FILE_PROCESSING_FAIL);
    }
}
