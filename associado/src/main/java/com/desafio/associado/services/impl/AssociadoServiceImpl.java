package com.desafio.associado.services.impl;

import com.desafio.associado.clients.boleto.dtos.BoletoDTO;
import com.desafio.associado.clients.boleto.services.BoletoService;
import com.desafio.associado.dtos.AssociadoRequest;
import com.desafio.associado.dtos.AssociadoResponse;
import com.desafio.associado.exceptions.ApiRequestBadRequest;
import com.desafio.associado.exceptions.ApiRequestNotFound;
import com.desafio.associado.models.Associado;
import com.desafio.associado.repositories.AssociadoRepository;
import com.desafio.associado.services.AssociadoService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.UUID;

@Log4j2
@Service
public class AssociadoServiceImpl implements AssociadoService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AssociadoRepository associadoRepository;

    @Autowired
    BoletoService boletoService;

    private static final String BOLETO_PAGO = "PAGO";

    @Override
    public AssociadoResponse criarAssociado(AssociadoRequest associadoRequest) {
        try {
            Associado associado = modelMapper.map(associadoRequest, Associado.class);
            log.info("Início: Salvar associado com documento {}", associadoRequest.getDocumento());
            Associado associadoSalvo = associadoRepository.save(associado);
            log.info("Fim: Salvar associado com documento {}", associadoRequest.getDocumento());
            return modelMapper.map(associadoSalvo, AssociadoResponse.class);
        } catch (Exception ex) {
            log.error("Erro ao salvar associado {}", associadoRequest);
            throw new RuntimeException("Erro ao salvar associado", ex);
        }
    }

    @Override
    public AssociadoResponse buscarAssociado(String documento) {
        log.info("Início: Buscar associados pelo documento {}", documento);
        Associado associado = associadoRepository.findByDocumento(documento)
                .orElseThrow(() -> new ApiRequestNotFound("Não foi possível achar associado com esse documento"));
        log.info("Fim: Buscar associados pelo documento {}", documento);
        return modelMapper.map(associado, AssociadoResponse.class);
    }

    @Override
    public AssociadoResponse buscarAssociadoPorId(UUID uuid) {
        log.info("Início: Buscar associados pelo id {}", uuid);
        Associado associado = associadoRepository.findById(uuid)
                .orElseThrow(() -> new ApiRequestNotFound("Não foi possível achar associado com esse id"));
        log.info("Fim: Buscar associados pelo id {}", uuid);
        return modelMapper.map(associado, AssociadoResponse.class);
    }

    @Override
    public AssociadoResponse atualizarAssociado(UUID uuid, AssociadoRequest associadoRequest) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        Associado associado = associadoRepository.findById(uuid)
                .orElseThrow(() -> new ApiRequestNotFound("Não foi possível achar associado com esse documento"));
        try {
            modelMapper.map(associadoRequest, associado);
            log.info("Início: Atualizar associado com documento {}", uuid);
            Associado associadoSalvo = associadoRepository.save(associado);
            log.info("Fim: Atualizar associado com documento {}", uuid);
            return modelMapper.map(associadoSalvo, AssociadoResponse.class);
        } catch (Exception ex) {
            log.error("Erro ao atualizar associado {}", associadoRequest);
            throw new RuntimeException("Erro ao atualizar associado", ex);
        }
    }

    @Override
    public void deletarAssociado(UUID uuid) {
        try {
            BoletoDTO boleto = boletoService.buscarBoletoPeloUuidAssociado(String.valueOf(uuid));
            if(boleto != null) {
                if(boleto.getSituacaoBoleto().equals(BOLETO_PAGO)) {
                    log.info("Início: Deletar associado com uuid {}", uuid);
                    associadoRepository.deleteById(uuid);
                    log.info("Fim: Deletar associado com uuid {}", uuid);
                } else {
                    log.error("Não é possível deletar associado com boleto pendente");
                    throw new ApiRequestBadRequest("Não é possível deletar associado com boleto pendente");
                }
            } else {
                log.error("Não foi possível achar boleto com esse uuid do associado");
                throw new ApiRequestNotFound("Não foi possível achar boleto com esse uuid do associado");
            }

        }catch (Exception ex) {
            log.error("Erro ao deletar associado com uuid {}", uuid);
            throw ex;
        }
    }
}
