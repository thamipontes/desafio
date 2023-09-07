package com.desafio.boleto.helpers;

import com.desafio.boleto.dtos.BoletoRequest;
import com.desafio.boleto.dtos.BoletoResponse;
import com.desafio.boleto.dtos.PagamentoBoletoRequest;
import com.desafio.boleto.enuns.ESituacaoBoleto;

public class BoletoHelper {

    public static BoletoResponse getBoletoResponse() {
        return new BoletoResponse("052a0588-7fa3-4324-8877-734c1b187564",
                1000.0f, "052a0588-7fa3-4324-8877-734c1b187564",
                "10-10-2012",
                "12345678910",
                "Nome do Pagador",
                "Nome Fantasia do Pagador",
                ESituacaoBoleto.NAO_PAGO);
    }

    public static BoletoResponse getBoletoResponsePago() {
        return new BoletoResponse("052a0588-7fa3-4324-8877-734c1b187564",
                1000.0f, "052a0588-7fa3-4324-8877-734c1b187564",
                "10-10-2012",
                "12345678910",
                "Nome do Pagador",
                "Nome Fantasia do Pagador",
                ESituacaoBoleto.PAGO);
    }

    public static BoletoRequest getBoletoRequest() {
        return new BoletoRequest(1000.0f,
                "10-10-2012",
                "12345678910",
                "Nome do Pagador",
                "Nome Fantasia do Pagador");
    }

    public static PagamentoBoletoRequest getPagamentoRequest() {
        return new PagamentoBoletoRequest("052a0588-7fa3-4324-8877-734c1b187564",
                1000.0f,
                "12345678910");
    }
}

