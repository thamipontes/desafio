package com.desafio.boleto.client.associado.service;

import com.desafio.boleto.client.associado.dto.AssociadoDTO;

public interface AssociadoService {
    AssociadoDTO buscarAssociado(String documento);
}
