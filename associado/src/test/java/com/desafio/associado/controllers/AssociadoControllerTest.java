package com.desafio.associado.controllers;

import com.desafio.associado.services.AssociadoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.desafio.associado.helpers.AssociadoHelpers.getAssociadoRequest;
import static com.desafio.associado.helpers.AssociadoHelpers.getAssociadoResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AssociadoController.class)
public class AssociadoControllerTest {
    @Autowired
    AssociadoController associadoController;

    @MockBean
    AssociadoService associadoService;

    @Test
    void test_criar_associado() {
        when(associadoService.criarAssociado(any()))
                .thenReturn(getAssociadoResponse());

        assertThat(associadoController.criarAssociado(getAssociadoRequest()))
                .usingRecursiveComparison()
                .isEqualTo(ResponseEntity.status(HttpStatus.CREATED).body(getAssociadoResponse()));
    }

    @Test
    void test_buscar_associado() {
        when(associadoService.buscarAssociado("123456789"))
                .thenReturn(getAssociadoResponse());

        assertThat(associadoController.buscarAssociado("123456789"))
                .usingRecursiveComparison()
                .isEqualTo(ResponseEntity.ok(getAssociadoResponse()));
    }

    @Test
    void test_buscar_associado_pelo_uuid() {
        when(associadoService.buscarAssociadoPorId(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")))
                .thenReturn(getAssociadoResponse());

        assertThat(associadoController.buscarAssociaoPorId("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"))
                .usingRecursiveComparison()
                .isEqualTo(ResponseEntity.ok(getAssociadoResponse()));
    }

    @Test
    void test_atualizar_associado() {
        when(associadoService.atualizarAssociado(any(), any()))
                .thenReturn(getAssociadoResponse());

        assertThat(associadoController.atualizarAssociado("a9b6ddd8-c1af-47c6-ad66-560a89c0281f", getAssociadoRequest()))
                .usingRecursiveComparison()
                .isEqualTo(ResponseEntity.ok(getAssociadoResponse()));
    }

    @Test
    void test_deletar_associado() {
        associadoController.deletarAssociado("a9b6ddd8-c1af-47c6-ad66-560a89c0281f");
        verify(associadoService, times(1)).deletarAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"));
    }
}
