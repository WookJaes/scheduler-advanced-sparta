package com.wookjae.scheduler.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 댓글을 찾을 수 없을 때 발생하는 예외 (404)
 */
public class CommentNotFoundException extends ServiceException {

    public CommentNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}