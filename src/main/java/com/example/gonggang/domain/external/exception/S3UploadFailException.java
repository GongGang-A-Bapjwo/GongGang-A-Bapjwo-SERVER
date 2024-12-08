package com.example.gonggang.domain.external.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class S3UploadFailException extends ServiceException {
    public S3UploadFailException() {
        super(ErrorCode.S3_UPLOAD_FAIL);
    }
}
