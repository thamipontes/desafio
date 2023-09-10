package com.desafio.boleto.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BoletoRequest {
    private Float valor;
    private String vencimento;
    private String documentoPagador;
    private String nomePagador;
    private String nomeFantasiaPagador;
}
