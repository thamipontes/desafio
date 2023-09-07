package com.desafio.associado.provider;

import com.desafio.associado.models.Associado;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class AssociadoGroupSequenceProvider implements DefaultGroupSequenceProvider<Associado>{

    @Override
    public List<Class<?>> getValidationGroups(Associado associado) {
        List<Class<?>> grupos = new ArrayList<>();
        grupos.add(Associado.class);

        if (associado != null) {
            if (associado.getTipoPessoa() != null) {
                grupos.add(associado.getTipoPessoa().getGrupo());
            }
        }

        return grupos;
    }
}
