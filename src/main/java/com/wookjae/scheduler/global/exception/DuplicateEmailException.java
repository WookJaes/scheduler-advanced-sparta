package com.wookjae.scheduler.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 이미 사용 중인 이메일로 회원가입 시 발생하는 예외 (409)
 */
public class DuplicateEmailException extends ServiceException {

    public DuplicateEmailException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}