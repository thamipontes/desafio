package com.desafio.associado.exceptions;

public class ApiRequestBadRequest extends RuntimeException{
    public ApiRequestBadRequest(String mensagem) {
        super(mensagem);
    }
}
