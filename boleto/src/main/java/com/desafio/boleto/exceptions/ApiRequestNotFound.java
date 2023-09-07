package com.desafio.boleto.exceptions;

public class ApiRequestNotFound extends RuntimeException{
    public ApiRequestNotFound(String mensagem) {
        super(mensagem);
    }
}
