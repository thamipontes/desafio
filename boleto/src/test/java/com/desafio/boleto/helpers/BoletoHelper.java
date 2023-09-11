package com.desafio.boleto.helpers;

import com.desafio.boleto.client.associado.dto.AssociadoDTO;
import com.desafio.boleto.dtos.BoletoRequest;
import com.desafio.boleto.dtos.BoletoResponse;
import com.desafio.boleto.dtos.PagamentoBoletoRequest;
import com.desafio.boleto.enuns.ESituacaoBoleto;
import com.desafio.boleto.models.Boleto;

import java.util.UUID;

import static com.desafio.boleto.utils.ConverteData.converterDataAtual;

public class BoletoHelper {

    public static BoletoResponse getBoletoResponse() {
        return new BoletoResponse("052a0588-7fa3-4324-8877-734c1b187564",
                1000.0f, "052a0588-7fa3-4324-8877-734c1b187564",
                converterDataAtual(),
                "91468816039",
                "Nome do Pagador",
                "Nome Fantasia do Pagador",
                ESituacaoBoleto.NAO_PAGO);
    }

    public static BoletoResponse getBoletoResponseJsonPago() {
        return new BoletoResponse("052a0588-7fa3-4324-8877-734c1b187564",
                230.04f, "6c916bd0-a318-411c-ba9f-16e463d7a2b3",
                "08-09-2023",
                "91468816039",
                "Teste Boleto",
                "Nome Fantasia do Pagador",
                ESituacaoBoleto.PAGO);
    }

    public static BoletoResponse getBoletoResponseJson() {
        return new BoletoResponse("052a0588-7fa3-4324-8877-734c1b187564",
                230.04f, "6c916bd0-a318-411c-ba9f-16e463d7a2b3",
                "08-09-2023",
                "91468816039",
                "Teste Boleto",
                "Nome Fantasia do Pagador",
                ESituacaoBoleto.NAO_PAGO);
    }

    public static BoletoResponse getBoletoResponsePago() {

        return new BoletoResponse("052a0588-7fa3-4324-8877-734c1b187564",
                1000.0f, "052a0588-7fa3-4324-8877-734c1b187564",
                converterDataAtual(),
                "91468816039",
                "Nome do Pagador",
                "Nome Fantasia do Pagador",
                ESituacaoBoleto.PAGO);
    }

    public static BoletoRequest getBoletoRequest() {
        BoletoRequest boletoRequest = new BoletoRequest();
        boletoRequest.setValor(1000.0f);
        boletoRequest.setVencimento(converterDataAtual());
        boletoRequest.setDocumentoPagador("91468816039");
        boletoRequest.setNomePagador("Nome do Pagador");
        boletoRequest.setNomeFantasiaPagador("Nome Fantasia do Pagador");
        return boletoRequest;
    }

    public static BoletoRequest getBoletoRequestVencimentoInvalido() {
        BoletoRequest boletoRequest = new BoletoRequest();
        boletoRequest.setVencimento("12-12-2012");
        return boletoRequest;
    }

    public static BoletoRequest getBoletoRequestError() {
        BoletoRequest boletoRequest = new BoletoRequest();
        boletoRequest.setDocumentoPagador("123456789");
        return boletoRequest;
    }

    public static PagamentoBoletoRequest getPagamentoRequest() {
        return new PagamentoBoletoRequest("052a0588-7fa3-4324-8877-734c1b187564",
                1000.0f,
                "91468816039");
    }

    public static PagamentoBoletoRequest getPagamentoRequestValorInvalido() {
        return new PagamentoBoletoRequest("052a0588-7fa3-4324-8877-734c1b187564",
                100.0f,
                "91468816039");
    }

    public static PagamentoBoletoRequest getPagamentoRequestErrorValor() {
         PagamentoBoletoRequest pagamentoBoletoRequest = new PagamentoBoletoRequest();
         pagamentoBoletoRequest.setValor(1001.0f);
         pagamentoBoletoRequest.setId("052a0588-7fa3-4324-8877-734c1b187564");
         pagamentoBoletoRequest.setDocumentoPagador("91468816039");

        return pagamentoBoletoRequest;
    }

    public static Boleto getBoleto(){
        return new Boleto("052a0588-7fa3-4324-8877-734c1b187564",
                1000.0f, UUID.fromString("052a0588-7fa3-4324-8877-734c1b187564"),
                converterDataAtual(),
                "91468816039",
                "Nome do Pagador",
                "Nome Fantasia do Pagador",
                ESituacaoBoleto.NAO_PAGO);
    }

    public static Boleto getBoletoPago(){
        return new Boleto("052a0588-7fa3-4324-8877-734c1b187564",
                1000.0f, UUID.fromString("052a0588-7fa3-4324-8877-734c1b187564"),
                converterDataAtual(),
                "91468816039",
                "Nome do Pagador",
                "Nome Fantasia do Pagador",
                ESituacaoBoleto.PAGO);
    }

    public static AssociadoDTO getAssociado(){
        return new AssociadoDTO("052a0588-7fa3-4324-8877-734c1b187564",
                "91468816039",
                "PF",
                "Nome do Pagador");
    }
}

