package com.wookjae.scheduler.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 일정을 찾을 수 없을 때 발생하는 예외 (404)
 */
public class ScheduleNotFoundException extends ServiceException {

    public ScheduleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}