package com.example.gonggang.domain.member.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class SocialTypeBadRequestExcepption extends ServiceException {
    public SocialTypeBadRequestExcepption() {
        super(ErrorCode.SOCIAL_TYPE_BAD_REQUEST);
    }
}
