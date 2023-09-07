package com.desafio.boleto.utils;

import com.desafio.boleto.dtos.PagamentoBoletoRequest;
import com.desafio.boleto.enuns.ESituacaoBoleto;
import com.desafio.boleto.exceptions.ApiRequestBadRequest;
import com.desafio.boleto.models.Boleto;

import java.time.LocalDate;

import static com.desafio.boleto.utils.ConverteData.converterData;

public class ValidacaoExcecao {
    public static void verificarValorBoleto(Boleto boleto, PagamentoBoletoRequest pagamentoBoletoRequest){
        if(boleto.getValor().compareTo(pagamentoBoletoRequest.getValor()) != 0) {
            throw new ApiRequestBadRequest("O valor do boleto não pode ser diferente do valor informado");
        }
    }

    public static void verificarSituacaoBoleto(Boleto boleto){
        if(boleto.getSituacaoBoleto().equals(ESituacaoBoleto.PAGO)) {
            throw new ApiRequestBadRequest("O boleto já foi pago");
        }
    }

    public static void verificarDataVencimento(String vencimento) {
        LocalDate vencimentoConvertido = converterData(vencimento);
        if(vencimentoConvertido.isBefore(LocalDate.now())){
            throw new ApiRequestBadRequest("A data de vencimento não pode ser menor que a data atual");
        }
    }
}
