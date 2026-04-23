package com.wookjae.scheduler.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 잘못된 요청으로 인해 처리할 수 없을 때 발생하는 예외 (400)
 */
public class BadRequestException extends ServiceException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}