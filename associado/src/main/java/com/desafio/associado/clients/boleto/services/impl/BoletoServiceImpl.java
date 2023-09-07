package com.desafio.associado.clients.boleto.services.impl;

import com.desafio.associado.clients.boleto.BoletoClient;
import com.desafio.associado.clients.boleto.dtos.BoletoDTO;
import com.desafio.associado.clients.boleto.services.BoletoService;
import com.desafio.associado.exceptions.ApiRequestIntegrationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class BoletoServiceImpl implements BoletoService {

    @Autowired
    private BoletoClient boletoClient;
    @Override
    public BoletoDTO buscarBoletoPeloUuidAssociado(String uuidAssociado) {
        try {
            log.info("Início: Busca por resultado na API boleto");
            BoletoDTO response = boletoClient.buscarBoletoPeloUuidAssociado(uuidAssociado);
            log.info("Fim: Busca por resultado na API boleto");
            return response;
        } catch (Exception ex) {
            throw new ApiRequestIntegrationException("Boleto não encontrado na API Boleto");
        }
    }
}
