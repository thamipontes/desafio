package com.desafio.associado.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomExceptionHandler.class)
public class CustomExceptionHandlerTest {

    @MockBean
    ConstraintViolationException constraintViolationException;

    @MockBean
    DataIntegrityViolationException dataIntegrityViolationException;

    @MockBean
    ApiRequestIntegrationException apiRequestIntegrationException;

    @MockBean
    ApiRequestNotFound apiRequestNotFound;

    @MockBean
    ApiRequestBadRequest apiRequestBadRequest;

    @Test
    public void test_handle_validation_exception() {
        when(constraintViolationException.getConstraintViolations()).thenReturn(Collections.emptySet());

        CustomExceptionHandler handler = new CustomExceptionHandler();

        ResponseEntity<ApiExceptionCustom> response = handler.handleValidationException(constraintViolationException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_handle_data_integrity() {
        when(dataIntegrityViolationException.getMessage()).thenReturn("Mensagem de erro");

        CustomExceptionHandler handler = new CustomExceptionHandler();

        ResponseEntity<ApiExceptionCustom> response = handler.handleValidationDataIntegrity(dataIntegrityViolationException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void handle_validation_integration_exception() {
        when(apiRequestIntegrationException.getMessage()).thenReturn("Mensagem de erro");

        CustomExceptionHandler handler = new CustomExceptionHandler();

        ResponseEntity<ApiExceptionCustom> response = handler.handleValidationIntegrationException(apiRequestIntegrationException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void handle_api_request_not_found() {
        when(apiRequestNotFound.getMessage()).thenReturn("Mensagem de erro");

        CustomExceptionHandler handler = new CustomExceptionHandler();

        ResponseEntity<ApiExceptionCustom> response = handler.handleApiRequestNotFound(apiRequestNotFound);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void handle_api_request_bad_request() {
        when(apiRequestBadRequest.getMessage()).thenReturn("Mensagem de erro");

        CustomExceptionHandler handler = new CustomExceptionHandler();

        ResponseEntity<ApiExceptionCustom> response = handler.handleApiRequestBadRequest(apiRequestBadRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
