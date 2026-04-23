package com.wookjae.scheduler.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 애플리케이션 전역에서 발생하는 예외를 처리하는 핸들러
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ServiceException 및 이를 상속한 예외를 처리한다.
     *
     * @param e 서비스 계층에서 발생한 공통 예외
     * @return 예외에 포함된 상태 코드와 메시지를 담은 응답
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return ResponseEntity
            .status(e.getStatus())
            .body(response);
    }

    /**
     * Valid 검증 실패 시 발생하는 예외를 처리한다.
     *
     * @param e 유효성 검사 실패 예외
     * @return 첫 번째 검증 실패 메시지를 포함한 400 응답
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e
    ) {
        String errorMessage = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .findFirst()
            .map(fieldError -> fieldError.getDefaultMessage())
            .orElse("입력 값이 올바르지 않습니다.");

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(errorMessage));
    }
}