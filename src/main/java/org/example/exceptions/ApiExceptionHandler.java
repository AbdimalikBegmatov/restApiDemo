package org.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException exception){
        HttpStatus httpStatus = exception.getStatus();
        ApiException apiException = new ApiException(
                httpStatus,
                exception.getMessage(),
                ZonedDateTime.now(ZoneId.of("Z")).withNano(0));
        return new ResponseEntity<>(apiException,httpStatus);
    }
}
