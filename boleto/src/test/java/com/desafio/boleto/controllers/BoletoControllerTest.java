package com.desafio.boleto.controllers;

import com.desafio.boleto.controllers.BoletoController;
import com.desafio.boleto.dtos.BoletoRequest;
import com.desafio.boleto.services.BoletoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.desafio.boleto.helpers.BoletoHelper.getBoletoRequest;
import static com.desafio.boleto.helpers.BoletoHelper.getBoletoResponse;
import static com.desafio.boleto.helpers.BoletoHelper.getBoletoResponsePago;
import static com.desafio.boleto.helpers.BoletoHelper.getPagamentoRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BoletoController.class)
public class BoletoControllerTest {
    @Autowired
    BoletoController boletoController;

    @MockBean
    private BoletoService boletoService;

    @Test
    void test_buscar_boleto_pelo_uuid_associado() {
        when(boletoService.buscarBoletoPeloUuidAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")))
                .thenReturn(getBoletoResponse());

        assertThat(boletoController.buscarBoletoPeloUuidAssociado("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"))
                .usingRecursiveComparison()
                .isEqualTo(ResponseEntity.ok(getBoletoResponse()));
    }

    @Test
    void test_criar_boleto() {
        BoletoRequest boletoRequest = getBoletoRequest();

        when(boletoService.criarBoleto(any()))
                .thenReturn(getBoletoResponse());

        assertThat(boletoController.criarBoleto(boletoRequest))
                .usingRecursiveComparison()
                .isEqualTo(ResponseEntity.status(HttpStatus.CREATED).body(getBoletoResponse()));

        verify(boletoService, times(1)).criarBoleto(eq(boletoRequest));
    }

    @Test
    void test_realizar_boleto() {
        when(boletoService.pagarBoleto(any()))
                .thenReturn(getBoletoResponsePago());

        assertThat(boletoController.pagarBoleto(getPagamentoRequest()))
                .usingRecursiveComparison()
                .isEqualTo(ResponseEntity.ok(getBoletoResponsePago()));
    }
}
