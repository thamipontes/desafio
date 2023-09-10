package com.desafio.associado.exceptions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiExceptionCustomTest {
    @Test
    public void testApiExceptionCustom() {

        String mensagem = "Mensagem de erro";
        ApiExceptionCustom exception = new ApiExceptionCustom(mensagem);

        assertEquals(mensagem, exception.getMensagem());
    }
}
