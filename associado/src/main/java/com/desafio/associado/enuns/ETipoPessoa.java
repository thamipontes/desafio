package com.desafio.associado.enuns;

import com.desafio.associado.provider.CnpjGroup;
import com.desafio.associado.provider.CpfGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ETipoPessoa {
    PF("000.000.000-00", CpfGroup.class),
    PJ("00.000.000/000-00", CnpjGroup.class);

    private String mascara;
    private Class<?> grupo;
}
