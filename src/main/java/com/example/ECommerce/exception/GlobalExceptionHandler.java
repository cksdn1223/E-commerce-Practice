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
