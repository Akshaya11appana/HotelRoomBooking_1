
// src/main/java/com/ey/exception/GlobalExceptionHandler.java
package com.ey.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Map<String,String>> runtime(RuntimeException ex){
    String msg = ex.getMessage() == null ? "Unexpected error" : ex.getMessage();
    HttpStatus status = msg.toLowerCase().contains("invalid") ? HttpStatus.UNAUTHORIZED
                    : msg.toLowerCase().contains("exists") ? HttpStatus.CONFLICT
                    : HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(status).body(Map.of("error", msg));
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String,String>> validation(MethodArgumentNotValidException ex){
    return ResponseEntity.badRequest().body(Map.of("error","Validation error"));
  }
}
