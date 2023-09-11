package com.desafio.boleto.utils;

import com.desafio.boleto.exceptions.ApiRequestBadRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.desafio.boleto.helpers.BoletoHelper.getBoleto;
import static com.desafio.boleto.helpers.BoletoHelper.getBoletoPago;
import static com.desafio.boleto.helpers.BoletoHelper.getPagamentoRequest;
import static com.desafio.boleto.helpers.BoletoHelper.getPagamentoRequestErrorValor;
import static com.desafio.boleto.utils.ConverteData.converterDataAtual;
import static com.desafio.boleto.utils.ValidacaoExcecao.verificarDataVencimento;
import static com.desafio.boleto.utils.ValidacaoExcecao.verificarSituacaoBoleto;
import static com.desafio.boleto.utils.ValidacaoExcecao.verificarValorBoleto;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ValidacaoExcecao.class)
public class ValidacaoExcecaoTest {
    @Test
    void test_verificar_valor_boleto_ok() {
        assertDoesNotThrow(() -> verificarValorBoleto(getBoleto(), getPagamentoRequest()));
    }

    @Test
    void test_verificar_valor_boleto_error() {
        assertThatExceptionOfType(ApiRequestBadRequest.class)
                .isThrownBy(() -> verificarValorBoleto(getBoleto(), getPagamentoRequestErrorValor()));
    }

    @Test
    void test_verificar_situacao_boleto_ok() {
        assertDoesNotThrow(() -> verificarSituacaoBoleto(getBoleto()));
    }

    @Test
    void test_verificar_situacao_boleto_error() {
        assertThatExceptionOfType(ApiRequestBadRequest.class)
                .isThrownBy(() -> verificarSituacaoBoleto(getBoletoPago()));
    }

    @Test
    void test_verificar_data_vencimento_ok() {
        assertDoesNotThrow(() -> verificarDataVencimento(converterDataAtual()));
    }

    @Test
    void test_verificar_data_vencimento_error() {
        assertThatExceptionOfType(ApiRequestBadRequest.class)
                .isThrownBy(() -> verificarDataVencimento("12-12-2012"));
    }
}
