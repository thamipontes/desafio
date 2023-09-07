package com.desafio.associado.services;

import com.desafio.associado.dtos.AssociadoRequest;
import com.desafio.associado.dtos.AssociadoResponse;

import java.util.UUID;

public interface AssociadoService {
    AssociadoResponse criarAssociado(AssociadoRequest associadoRequest);
    AssociadoResponse buscarAssociado(String documento);
    AssociadoResponse buscarAssociadoPorId(UUID uuid);
    AssociadoResponse atualizarAssociado(UUID uuid, AssociadoRequest associadoRequest);
    void deletarAssociado(UUID documento);

}
