package com.project.neurohelp.platform.exception;

import com.project.neurohelp.platform.rest.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NeuroHelpExceptionHandler {
    @ExceptionHandler(NeuroHelpException.class)
    public ResponseEntity<?> handle(NeuroHelpException exception) {
        return ResponseEntity.badRequest().body(RestResponse.error(exception.getMessage()));
    }
}
