package com.crustwerk.restapi;

import com.crustwerk.restapi.exception.ApiError;
import com.crustwerk.restapi.exception.EmailAlreadyUsedException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @@ResponseBody Funziona similmente a @RequestBody e indica a Spring di non rifarsi all'URI per comunicare col client ma a un oggetto.
     * In entrata costruisco il DTO dal JSON (e non da una query parametrica) e in uscita inserisco il ritorno in un body
     * (anzichè costruire un URI per far accedere il client a una risorsa, vedi es. sotto).
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        String message = "Validation failed for one or more fields.";

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, message);
        error.setFieldErrors(fieldErrors);

        // return "ciolla" <- senza @ResponseBody Spring risponderebbe al client puntando alla risorsa api/users/ciolla
        // nel caso di una ResponseEntity non è necessario @ResponseBody perché Spring conosce il tipo e sa già cosa fare
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    //TODO
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        String message = "Malformed JSON or invalid data format.";
        Map<String, String> fieldErrors = new HashMap<>();

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidFormat && !invalidFormat.getPath().isEmpty()) {
            String fieldName = invalidFormat.getPath().get(0).getFieldName();
            fieldErrors.put(fieldName, "Invalid format. Expected format: yyyy-MM-dd");
            message = "Invalid format for field: " + fieldName;
        }

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, message);
        error.setFieldErrors(fieldErrors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyUsed(EmailAlreadyUsedException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}