package com.desafio.associado.enuns;

import com.desafio.associado.provider.CnpjGroup;
import com.desafio.associado.provider.CpfGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ETipoPessoa {
    PF(CpfGroup.class),
    PJ(CnpjGroup.class);

    private Class<?> grupo;
}
