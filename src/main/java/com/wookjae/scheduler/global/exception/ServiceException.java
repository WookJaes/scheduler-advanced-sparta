package com.wookjae.scheduler.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 애플리케이션 전반에서 사용하는 공통 예외 클래스
 */
@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus status;

    /**
     * 공통 예외 객체를 생성한다.
     *
     * @param status HTTP 상태 코드
     * @param message 에러 메시지
     */
    public ServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}