package com.wookjae.scheduler.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 일정당 댓글 최대 개수(10개)를 초과했을 때 발생하는 예외 (400)
 */
public class CommentLimitExceededException extends ServiceException {

	public CommentLimitExceededException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}