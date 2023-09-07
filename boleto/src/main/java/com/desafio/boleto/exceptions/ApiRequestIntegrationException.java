package com.desafio.boleto.exceptions;

public class ApiRequestIntegrationException extends RuntimeException{
    public ApiRequestIntegrationException(String mensagem) {
        super(mensagem);
    }
}
