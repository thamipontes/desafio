package com.desafio.associado.services;

import com.desafio.associado.clients.boleto.services.BoletoService;
import com.desafio.associado.exceptions.ApiRequestBadRequest;
import com.desafio.associado.exceptions.ApiRequestNotFound;
import com.desafio.associado.repositories.AssociadoRepository;
import com.desafio.associado.services.impl.AssociadoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static com.desafio.associado.helpers.AssociadoHelpers.getAssociado;
import static com.desafio.associado.helpers.AssociadoHelpers.getAssociadoRequest;
import static com.desafio.associado.helpers.AssociadoHelpers.getAssociadoResponse;
import static com.desafio.associado.helpers.AssociadoHelpers.getBoletoDTO;
import static com.desafio.associado.helpers.AssociadoHelpers.getBoletoDTOPendente;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        AssociadoServiceImpl.class, ModelMapper.class
})
public class AssociadoServiceTest {
    @Autowired
    AssociadoService associadoService;

    @MockBean
    AssociadoRepository associadoRepository;

    @MockBean
    BoletoService boletoService;


    @Test
    void test_criar_associado_ok() {
        when(associadoRepository.save(any())).thenReturn(getAssociado());
        assertThat(associadoService.criarAssociado(getAssociadoRequest()))
                .usingRecursiveComparison()
                .isEqualTo(getAssociadoResponse());
    }

    @Test
    void test_criar_associado_error() {
        when(associadoRepository.save(any())).thenThrow(new RuntimeException());
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> associadoService.criarAssociado(getAssociadoRequest()));
    }

    @Test
    void test_buscar_associado_ok() {
        when(associadoRepository.findByDocumento("12345678910")).thenReturn(Optional.of(getAssociado()));
        assertThat(associadoService.buscarAssociado("12345678910"))
                .usingRecursiveComparison()
                .isEqualTo(getAssociadoResponse());
    }

    @Test
    void test_buscar_associado_error() {
        when(associadoRepository.findByDocumento("12345678910")).thenReturn(Optional.empty());
        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> associadoService.buscarAssociado("12345678910"));
    }

    @Test
    void test_buscar_associado_por_id_ok() {
        when(associadoRepository.findById(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"))).thenReturn(Optional.of(getAssociado()));
        assertThat(associadoService.buscarAssociadoPorId(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")))
                .usingRecursiveComparison()
                .isEqualTo(getAssociadoResponse());
    }

    @Test
    void test_buscar_associado_por_id_error(){
        when(associadoRepository.findById(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"))).thenReturn(Optional.empty());
        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> associadoService.buscarAssociadoPorId(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")));
    }

    @Test
    void test_atualizar_associado_ok() {
        when(associadoRepository.findById(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"))).thenReturn(Optional.of(getAssociado()));
        when(associadoRepository.save(any())).thenReturn(getAssociado());
        assertThat(associadoService.atualizarAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"), getAssociadoRequest()))
                .usingRecursiveComparison()
                .isEqualTo(getAssociadoResponse());
    }

    @Test
    void test_atualizar_associado_error_associado_nulo() {
        when(associadoRepository.findById(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"))).thenReturn(Optional.empty());
        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> associadoService.atualizarAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"), getAssociadoRequest()));
    }

    @Test
    void test_atualizar_associado_error() {
        when(associadoRepository.findById(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"))).thenReturn(Optional.of(getAssociado()));
        when(associadoRepository.save(any())).thenThrow(RuntimeException.class);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> associadoService.atualizarAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"), getAssociadoRequest()));
    }

    @Test
    void test_deletar_associado_ok() {
        when(boletoService.buscarBoletoPeloUuidAssociado("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")).thenReturn(getBoletoDTO());
        associadoService.deletarAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"));
        verify(associadoRepository, times(1)).deleteById(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"));
    }

    @Test
    void test_deletar_associado_error_boleto_nulo() {
        when(boletoService.buscarBoletoPeloUuidAssociado("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")).thenReturn(null);
        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> associadoService.deletarAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")));
    }

    @Test
    void test_deletar_associado_error_boleto_pendente() {
        when(boletoService.buscarBoletoPeloUuidAssociado("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")).thenReturn(getBoletoDTOPendente());
        assertThatExceptionOfType(ApiRequestBadRequest.class)
                .isThrownBy(() -> associadoService.deletarAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")));
    }

    @Test
    void test_deletar_associado_error() {
        when(boletoService.buscarBoletoPeloUuidAssociado("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")).thenThrow(RuntimeException.class);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> associadoService.deletarAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")));
    }
}
