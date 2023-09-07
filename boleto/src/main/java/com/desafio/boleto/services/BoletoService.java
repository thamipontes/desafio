package com.desafio.boleto.services;

import com.desafio.boleto.dtos.BoletoRequest;
import com.desafio.boleto.dtos.BoletoResponse;
import com.desafio.boleto.dtos.PagamentoBoletoRequest;

import java.util.UUID;

public interface BoletoService {
    BoletoResponse criarBoleto(BoletoRequest boletoRequest);
    BoletoResponse buscarBoletoPeloUuidAssociado(UUID uuidAssociado);
    BoletoResponse pagarBoleto(PagamentoBoletoRequest pagamentoBoletoRequest);
}
