package com.example.gonggang.domain.member.exception;

import com.example.gonggang.global.config.error.ErrorCode;
import com.example.gonggang.global.config.error.exception.ServiceException;

public class MemberNotFoundExcepption extends ServiceException {
    public MemberNotFoundExcepption() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
}
