package com.desafio.associado.exceptions;

public class ApiRequestNotFound extends RuntimeException{
    public ApiRequestNotFound(String mensagem) {
        super(mensagem);
    }
}
