package com.wookjae.scheduler.global.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends ServiceException {

    public DuplicateEmailException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}