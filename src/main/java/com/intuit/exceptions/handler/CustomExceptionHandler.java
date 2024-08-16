package com.intuit.exceptions.handler;

import com.intuit.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({PostNotFoundException.class, CommentsNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
    }

    @ExceptionHandler({InvalidUuidException.class, InvalidEnumValueException.class, RequestObjectNullException.class, PageSizeZeroException.class})
    public ResponseEntity<Object> handleInvalidRequestException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
