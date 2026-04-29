package com.project.neurohelp.platform.exception;

import com.project.neurohelp.platform.rest.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception exception) {
        return ResponseEntity.badRequest().body(RestResponse.error(exception.getMessage()));
    }
}
