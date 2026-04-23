package com.wookjae.scheduler.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 권한이 없는 유저가 접근할 때 발생하는 예외 (403)
 */
public class ForbiddenException extends ServiceException {

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}