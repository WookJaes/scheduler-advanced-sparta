package com.wookjae.scheduler.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 인증되지 않은 유저가 접근할 때 발생하는 예외 (401)
 */
public class UnauthorizedException extends ServiceException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}