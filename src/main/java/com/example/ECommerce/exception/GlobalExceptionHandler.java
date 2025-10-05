package com.example.ECommerce.exception;

import com.example.ECommerce.dto.Error.ErrorResponseRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice   // @RestController에서 발생하는 예외를 전역적으로 처리합니다.
public class GlobalExceptionHandler {
    /**
     * 리소스를 찾지 못했을 때 발생하는 사용자 지정 예외
     * @param ex - ResourceNotFoundException
     * @return 404 HttpStatus.NOT_FOUND
     */
    // @ExceptionHandler: 특정 예외 클래스를 지정하여 처리할 메소드를 정의합니다.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseRecord> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponseRecord errorResponseRecord = new ErrorResponseRecord(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""));
        // 구성된 에러 메시지와 함께 HTTP 404 (Not Found) 상태 코드를 응답합니다.
        return new ResponseEntity<>(errorResponseRecord, HttpStatus.NOT_FOUND);
    }

    /**
     * 권한이 없을 때 발생하는 예외 처리
     * @param ex - AccessDeniedException
     * @return 403 HttpStatus.FORBIDDEN
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseRecord> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ErrorResponseRecord errorResponseRecord = new ErrorResponseRecord(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponseRecord, HttpStatus.FORBIDDEN);
    }

    /**
     * Todo의 Content를 조작할때 비어있거나 공백이면 발생하는 예외 처리
     * @param ex - NoContentException
     * @return 400 HttpStatus.BAD_REQUEST
     */
    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ErrorResponseRecord> handleNoContentException(NoContentException ex, WebRequest request) {
        ErrorResponseRecord errorResponseRecord = new ErrorResponseRecord(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponseRecord, HttpStatus.BAD_REQUEST);
    }

    /**
     * 가입하려는 유저의 이름이 이미 DB에 있을때 발생하는 예외 처리
     * @param ex UsernameAlreadyExistsException
     * @return 409 HttpStatus.CONFLICT
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseRecord> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {
        ErrorResponseRecord errorResponseRecord = new ErrorResponseRecord(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponseRecord, HttpStatus.CONFLICT);
    }


}
