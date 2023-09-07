package com.desafio.boleto.dtos;

import com.desafio.boleto.enuns.ESituacaoBoleto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BoletoResponse {
    private String id;
    private Float valor;
    private String uuidAssociado;
    private String vencimento;
    private String documentoPagador;
    private String nomePagador;
    private String nomeFantasiaPagador;
    private ESituacaoBoleto situacaoBoleto;
}
