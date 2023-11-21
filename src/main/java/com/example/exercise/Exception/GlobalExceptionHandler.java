package com.example.exercise.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<Object> handleCustomException(CustomException ex) {
    return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
  }
}
