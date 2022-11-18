package com.example.demo.config;

import com.example.demo.tinytypes.validation.ValidationException;
import java.time.Clock;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final Clock clock;

  public GlobalExceptionHandler(Clock clock) {
    this.clock = clock;
  }

  @NotNull
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      @NotNull HttpMessageNotReadableException ex,
      @NotNull HttpHeaders headers,
      @NotNull HttpStatus status,
      @NotNull WebRequest request) {

    if (ex.getRootCause() instanceof ValidationException exception) {
      var error = new ApiError(
          Instant.now(clock),
          status.value(),
          status.getReasonPhrase(),
          exception.getClass().getSimpleName(),
          exception.getMessage()
      );
      return handleExceptionInternal(ex, error, headers, status, request);
    }

    return super.handleHttpMessageNotReadable(ex, headers, status, request);
  }

  record ApiError(
      Instant timestamp,
      Integer status,
      String error,
      String exception,
      String message
  ) {

  }
}