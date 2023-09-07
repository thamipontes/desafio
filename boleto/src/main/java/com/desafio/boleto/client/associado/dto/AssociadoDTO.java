package com.desafio.boleto.client.associado.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AssociadoDTO {
    private String id;
    private String documento;
    private String tipoPessoa;
    private String nome;
}
