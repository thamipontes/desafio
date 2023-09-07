package com.desafio.associado.exceptions;

public class ApiRequestIntegrationException extends RuntimeException{
    public ApiRequestIntegrationException(String mensagem) {
        super(mensagem);
    }
}
