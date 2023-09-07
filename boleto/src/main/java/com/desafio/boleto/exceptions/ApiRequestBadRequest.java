package com.desafio.boleto.exceptions;

public class ApiRequestBadRequest extends RuntimeException{
    public ApiRequestBadRequest(String mensagem) {
        super(mensagem);
    }
}
