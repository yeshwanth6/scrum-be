package com.example.exercise.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class RetrospectiveValidationException extends CustomException {
  public RetrospectiveValidationException(String message) {
    super(message, HttpStatus.BAD_REQUEST);
    log.error(message);
  }
}
