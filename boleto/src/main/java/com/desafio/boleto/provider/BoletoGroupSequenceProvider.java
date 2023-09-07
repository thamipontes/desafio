package com.desafio.boleto.provider;

import com.desafio.boleto.models.Boleto;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class BoletoGroupSequenceProvider implements DefaultGroupSequenceProvider<Boleto>{

    @Override
    public List<Class<?>> getValidationGroups(Boleto boleto) {
        List<Class<?>> grupos = new ArrayList<>();
        grupos.add(Boleto.class);

        if (boleto != null) {
            if (boleto.getDocumentoPagador() != null) {
                if(boleto.getDocumentoPagador().length() == 11) {
                    grupos.add(CpfGroup.class);
                } else {
                    grupos.add(CnpjGroup.class);
                }
            }
        }
        return grupos;
    }
}
