package com.example.exercise.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class RetrospectiveNotFoundException extends CustomException {
  public RetrospectiveNotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND);
    log.error(message);
  }
}
