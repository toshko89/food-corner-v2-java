package com.example.food_corner_v2_java.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Map<String,String>> handleAppException(AppException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.printStackTrace();

        errors.put("message", ex.getMessage());

        return ResponseEntity.status(ex.getHttpStatus()).body(errors);
    }
}