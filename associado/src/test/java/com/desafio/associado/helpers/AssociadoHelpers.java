package com.desafio.associado.helpers;

import com.desafio.associado.clients.boleto.dtos.BoletoDTO;
import com.desafio.associado.dtos.AssociadoRequest;
import com.desafio.associado.dtos.AssociadoResponse;
import com.desafio.associado.enuns.ETipoPessoa;
import com.desafio.associado.models.Associado;

import java.util.UUID;

import static com.desafio.associado.utils.ConverteData.converterDataAtual;

public class AssociadoHelpers {

    public static AssociadoResponse getAssociadoResponse() {
        return new AssociadoResponse("a9b6ddd8-c1af-47c6-ad66-560a89c0281f", "81775744000", ETipoPessoa.PF, "João da Silva");
    }

    public static AssociadoRequest getAssociadoRequestPut() {
        AssociadoRequest associadoRequest = new AssociadoRequest();
        associadoRequest.setNome("João da Silva Carvalho");

        return associadoRequest;
    }

    public static AssociadoRequest getAssociadoRequest() {
        return new AssociadoRequest("81775744000", ETipoPessoa.PF, "João da Silva");
    }
    public static AssociadoRequest getAssociadoRequestCpf() {
        return new AssociadoRequest("81775744000", ETipoPessoa.PF, "João da Silva");
    }

    public static AssociadoRequest getAssociadoRequestCnpj() {
        return new AssociadoRequest("95730385000159", ETipoPessoa.PJ, "João da Silva");
    }


    public static AssociadoRequest getAssociadoRequestErrorCpf() {
        return new AssociadoRequest("81775744003", ETipoPessoa.PF, "João da Silva");
    }

    public static AssociadoRequest getAssociadoRequestErrorCnpj() {
        return new AssociadoRequest("95730385000153", ETipoPessoa.PF, "João da Silva");
    }

    public static Associado getAssociado() {
        return new Associado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"), "81775744000", ETipoPessoa.PF, "João da Silva");
    }

    public static Associado getAssociadoCpf() {
        return new Associado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"), "81775744000", ETipoPessoa.PF, "João da Silva");
    }

    public static Associado getAssociadoCnpj() {
        return new Associado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"), "95730385000159", ETipoPessoa.PJ, "João da Silva");
    }
    public static BoletoDTO getBoletoDTO() {
        return new BoletoDTO("a9b6ddd8-c1af-47c6-ad66-560a89c0281f", 1000.0f, "052a0588-7fa3-4324-8877-734c1b187564", converterDataAtual(), "81775744000", "Nome do Pagador", "Nome Fantasia do Pagador", "PAGO");
    }

    public static BoletoDTO getBoletoDTOPendente() {
        BoletoDTO boletoDTO = new BoletoDTO();
        boletoDTO.setId("a9b6ddd8-c1af-47c6-ad66-560a89c0281f");
        boletoDTO.setValor(1000.0f);
        boletoDTO.setUuidAssociado("052a0588-7fa3-4324-8877-734c1b187564");
        boletoDTO.setVencimento(converterDataAtual());
        boletoDTO.setDocumentoPagador("81775744000");
        boletoDTO.setNomePagador("Nome do Pagador");
        boletoDTO.setNomeFantasiaPagador("Nome Fantasia do Pagador");
        boletoDTO.setSituacaoBoleto("NAO_PAGO");
        return boletoDTO;
    }
}
