package com.desafio.boleto.exceptions;

public class ApiExceptionCustom {
    private final String mensagem;

    public ApiExceptionCustom(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
