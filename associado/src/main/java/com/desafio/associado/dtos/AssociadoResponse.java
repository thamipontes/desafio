package com.desafio.associado.dtos;

import com.desafio.associado.enuns.ETipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoResponse {
    private String id;
    private String documento;
    private ETipoPessoa tipoPessoa;
    private String nome;
}
