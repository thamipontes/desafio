package com.desafio.associado.clients.boleto.services;

import com.desafio.associado.clients.boleto.dtos.BoletoDTO;

public interface BoletoService {
    BoletoDTO buscarBoletoPeloUuidAssociado(String uuidAssociado);
}
