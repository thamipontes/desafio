package com.desafio.boleto.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PagamentoBoletoRequest {
    private String id;
    private Float valor;
    private String documentoPagador;
}
