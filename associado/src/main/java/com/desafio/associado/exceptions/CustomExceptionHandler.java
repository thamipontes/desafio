package com.desafio.associado.exceptions;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ApiExceptionCustom> handleValidationException(ConstraintViolationException ex) {
        ApiExceptionCustom apiExceptionCustom = new ApiExceptionCustom(ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", ")));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiExceptionCustom);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<ApiExceptionCustom> handleValidationDataIntegrity(DataIntegrityViolationException ex) {
        ApiExceptionCustom apiExceptionCustom = new ApiExceptionCustom(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiExceptionCustom);
    }

    @ExceptionHandler(ApiRequestIntegrationException.class)
    @ResponseBody
    public ResponseEntity<ApiExceptionCustom> handleValidationIntegrationException(ApiRequestIntegrationException ex) {
        ApiExceptionCustom apiExceptionCustom = new ApiExceptionCustom(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiExceptionCustom);
    }

    @ExceptionHandler(ApiRequestNotFound.class)
    @ResponseBody
    public ResponseEntity<ApiExceptionCustom> handleApiRequestNotFound(ApiRequestNotFound ex) {
        ApiExceptionCustom apiExceptionCustom = new ApiExceptionCustom(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiExceptionCustom);
    }

    @ExceptionHandler(ApiRequestBadRequest.class)
    @ResponseBody
    public ResponseEntity<ApiExceptionCustom> handleApiRequestBadRequest(ApiRequestBadRequest ex) {
        ApiExceptionCustom apiExceptionCustom = new ApiExceptionCustom(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiExceptionCustom);
    }

}