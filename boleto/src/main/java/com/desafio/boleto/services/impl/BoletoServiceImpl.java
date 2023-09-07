package com.desafio.boleto.services.impl;

import com.desafio.boleto.client.associado.dto.AssociadoDTO;
import com.desafio.boleto.client.associado.service.AssociadoService;
import com.desafio.boleto.dtos.BoletoRequest;
import com.desafio.boleto.dtos.BoletoResponse;
import com.desafio.boleto.dtos.PagamentoBoletoRequest;
import com.desafio.boleto.enuns.ESituacaoBoleto;
import com.desafio.boleto.exceptions.ApiRequestNotFound;
import com.desafio.boleto.models.Boleto;
import com.desafio.boleto.repositories.BoletoRepository;
import com.desafio.boleto.services.BoletoService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.desafio.boleto.utils.ValidacaoExcecao.verificarDataVencimento;
import static com.desafio.boleto.utils.ValidacaoExcecao.verificarSituacaoBoleto;
import static com.desafio.boleto.utils.ValidacaoExcecao.verificarValorBoleto;

@Log4j2
@Service
public class BoletoServiceImpl implements BoletoService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AssociadoService associadoService;

    @Autowired
    BoletoRepository boletoRepository;

    @Override
    public BoletoResponse criarBoleto(BoletoRequest boletoRequest) {
        try {
            Boleto boleto = preencherBoleto(boletoRequest);
            log.info("Início: Salvar boleto com documento {}", boletoRequest.getDocumentoPagador());
            Boleto boletoSalvo = boletoRepository.save(boleto);
            log.info("Fim: Salvar boleto com documento {}", boletoRequest.getDocumentoPagador());
            return modelMapper.map(boletoSalvo, BoletoResponse.class);
        } catch (Exception ex) {
            log.error("Erro ao salvar boleto {}", boletoRequest);
            throw new RuntimeException("Erro ao salvar associado", ex);
        }
    }

    @Override
    public BoletoResponse buscarBoletoPeloUuidAssociado(UUID uuidAssociado) {
        log.info("Início: Buscar boleto pelo uuid do associado {}", uuidAssociado);
        Boleto boleto = boletoRepository.findByUuidAssociado(uuidAssociado)
                .orElseThrow(() -> new ApiRequestNotFound("Não foi possível achar boleto com esse uuid do associado"));
        log.info("Fim: Buscar boleto pelo uuid do associado {}", uuidAssociado);
        return modelMapper.map(boleto, BoletoResponse.class);
    }

    @Override
    public BoletoResponse pagarBoleto(PagamentoBoletoRequest pagamentoBoletoRequest) {
        log.info("Início: Buscar boleto {}", pagamentoBoletoRequest);
        Boleto boleto = boletoRepository.findByPagamentoBoleto(UUID.fromString(pagamentoBoletoRequest.getId()),
                pagamentoBoletoRequest.getValor(), pagamentoBoletoRequest.getDocumentoPagador())
                .orElseThrow(() -> new ApiRequestNotFound("Não foi possível achar boleto."));
        log.info("Fim: Buscar boleto pelo {}", pagamentoBoletoRequest);
        Boleto boletoPago = realizarPagamento(boleto, pagamentoBoletoRequest);
        boletoRepository.save(boletoPago);
        return modelMapper.map(boletoPago, BoletoResponse.class);
    }

    private Boleto preencherBoleto(BoletoRequest boletoRequest) {
        verificarDataVencimento(boletoRequest.getVencimento());
        Boleto boleto = modelMapper.map(boletoRequest, Boleto.class);
        AssociadoDTO associado = associadoService.buscarAssociado(boletoRequest.getDocumentoPagador());
        if (associado != null) {
            boleto.setUuidAssociado(UUID.fromString(associado.getId()));
        } else {
            throw new ApiRequestNotFound("Não foi possível achar associado com esse documento");
        }
        boleto.setSituacaoBoleto(ESituacaoBoleto.NAO_PAGO);
        return boleto;
    }

    private Boleto realizarPagamento(Boleto boleto, PagamentoBoletoRequest pagamentoBoletoRequest) {
        verificarValorBoleto(boleto, pagamentoBoletoRequest);
        verificarSituacaoBoleto(boleto);
        boleto.setSituacaoBoleto(ESituacaoBoleto.PAGO);
        return boleto;
    }

}
