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

/**
 * {@link org.springframework.http.converter.HttpMessageNotReadableException}
 * Viene sollevata quando la deserializzazione del corpo della richiesta fallisce (es. data non conforme a {@code @JsonFormat}).
 * <br>
 * {@link org.springframework.web.bind.MethodArgumentNotValidException}
 * Viene sollevata quando fallisce la validazione di un parametro annotato con {@code @Valid} o {@code @Validated}.
 * <br><br>
 * NB: le valiazioni vengono verificate solo se la deserializzazione ha avuto esito positivo.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @HttpMessageNotReadableException viene sollevata da Spring quando c'è un problema nella lettura del corpo della richiesta.
     * Nel caso di un formato di dati non valido, questa eccezione è un wrapper per InvalidFormatException, che viene automaticamente gestita da Spring.
     * @InvalidFormatException è sollevata da Jackson quando un dato nel JSON non può essere convertito nel tipo Java desiderato (come una data che non corrisponde al formato yyyy-MM-dd).
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        String message = "Malformed JSON or invalid data format.";
        Map<String, String> fieldErrors = new HashMap<>();

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidFormat && !invalidFormat.getPath().isEmpty()) {
            fieldErrors.put(invalidFormat.getPath().get(0).getFieldName(), "Invalid format. Expected format: yyyy-MM-dd");
            message = "Invalid format(s) for one or more fields.";
        }

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, message);
        error.setFieldErrors(fieldErrors);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * @@ResponseBody fa sì che l'oggetto restituito dal metodo venga serializzato nel body della risposta HTTP,
     * anziché cercare una view o redirezionare.
     * Fondamentale nei controller REST per scambiare oggetti JSON con il client.
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


    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyUsed(EmailAlreadyUsedException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}