package com.desafio.associado.clients.boleto.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BoletoDTO {
    private String id;
    private Float valor;
    private String uuidAssociado;
    private String vencimento;
    private String documentoPagador;
    private String nomePagador;
    private String nomeFantasiaPagador;
    private String situacaoBoleto;
}
