package com.crustwerk.restapi;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidFormat) {
            String fieldName = "";

            if (!invalidFormat.getPath().isEmpty()) {
                fieldName = invalidFormat.getPath().get(0).getFieldName(); // Es: "dateOfBirth"
            }

            error.put(fieldName, "Invalid format. Expected format: yyyy-MM-dd");

        } else {
            error.put("error", "Malformed JSON or invalid data format.");
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}