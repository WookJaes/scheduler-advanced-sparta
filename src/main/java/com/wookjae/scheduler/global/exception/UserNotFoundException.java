package com.wookjae.scheduler.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 유저를 찾을 수 없을 때 발생하는 예외 (404)
 */
public class UserNotFoundException extends ServiceException {

    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}