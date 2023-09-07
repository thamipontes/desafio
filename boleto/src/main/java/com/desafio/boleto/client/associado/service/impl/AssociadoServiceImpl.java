package com.desafio.boleto.client.associado.service.impl;

import com.desafio.boleto.client.associado.AssociadoClient;
import com.desafio.boleto.client.associado.dto.AssociadoDTO;
import com.desafio.boleto.client.associado.service.AssociadoService;
import com.desafio.boleto.exceptions.ApiRequestIntegrationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AssociadoServiceImpl implements AssociadoService {
    @Autowired
    private AssociadoClient associadoClient;

    @Override
    public AssociadoDTO buscarAssociado(String documento) {
        try {
            log.info("Início: Busca por resultado na API associado");
            AssociadoDTO response = associadoClient.buscarAssociado(documento);
            log.info("Fim: Busca por resultado na API associado");
            return response;
        } catch (Exception ex) {
            throw new ApiRequestIntegrationException("Usuário não encontrado na API associado");
        }
    }
}
