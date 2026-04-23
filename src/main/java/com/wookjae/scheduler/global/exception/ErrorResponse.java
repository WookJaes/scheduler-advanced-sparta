package com.wookjae.scheduler.global.exception;

import lombok.Getter;

/**
 * 예외 발생 시 클라이언트에 반환되는 공통 에러 응답 DTO
 */
@Getter
public class ErrorResponse {

    private final String message;

    /**
     * 에러 메시지를 포함한 응답 객체를 생성한다.
     *
     * @param message 클라이언트에 전달할 에러 메시지
     */
    public ErrorResponse(String message) {
        this.message = message;
    }
}