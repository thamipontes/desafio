package com.desafio.boleto.services;

import com.desafio.boleto.client.associado.service.AssociadoService;
import com.desafio.boleto.exceptions.ApiRequestBadRequest;
import com.desafio.boleto.exceptions.ApiRequestNotFound;
import com.desafio.boleto.models.Boleto;
import com.desafio.boleto.repositories.BoletoRepository;
import com.desafio.boleto.services.impl.BoletoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static com.desafio.boleto.helpers.BoletoHelper.getAssociado;
import static com.desafio.boleto.helpers.BoletoHelper.getBoleto;
import static com.desafio.boleto.helpers.BoletoHelper.getBoletoPago;
import static com.desafio.boleto.helpers.BoletoHelper.getBoletoRequest;
import static com.desafio.boleto.helpers.BoletoHelper.getBoletoRequestVencimentoInvalido;
import static com.desafio.boleto.helpers.BoletoHelper.getBoletoResponse;
import static com.desafio.boleto.helpers.BoletoHelper.getBoletoResponsePago;
import static com.desafio.boleto.helpers.BoletoHelper.getPagamentoRequest;
import static com.desafio.boleto.helpers.BoletoHelper.getPagamentoRequestValorInvalido;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        BoletoServiceImpl.class, ModelMapper.class
})
public class BoletoServiceTest {
    @Autowired
    private BoletoService boletoService;

    @MockBean
    private AssociadoService associadoService;

    @MockBean
    private BoletoRepository boletoRepository;

    @Test
    void test_criar_boleto_ok() {

        Boleto boleto = getBoleto();

        when(associadoService.buscarAssociado("91468816039"))
                .thenReturn(getAssociado());

        when(boletoRepository.save(any())).thenReturn(boleto);

        assertThat(boletoService.criarBoleto(getBoletoRequest()))
                .usingRecursiveComparison()
                .isEqualTo(getBoletoResponse());
    }

    @Test
    void test_criar_boleto_error_com_associado() {

        when(associadoService.buscarAssociado("12345678910"))
                .thenReturn(getAssociado());

        assertThrows(
                RuntimeException.class,
                () -> {
                    boletoService.criarBoleto(getBoletoRequest());
                }
        );
    }

    @Test
    void test_criar_boleto_error_vencimento_invalido() {
        when(associadoService.buscarAssociado("12345678910"))
                .thenReturn(getAssociado());

        assertThatExceptionOfType(ApiRequestBadRequest.class)
                .isThrownBy(() -> boletoService.criarBoleto(getBoletoRequestVencimentoInvalido()));
    }

    @Test
    void test_criar_boleto_error_sem_associado() {
        when(associadoService.buscarAssociado("12345678910"))
                .thenReturn(null);

        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> boletoService.criarBoleto(getBoletoRequest()));
    }

    @Test
    void test_buscar_boleto_pelo_uuid_associado_ok() {
        when(boletoRepository.findByUuidAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")))
                .thenReturn(Optional.of(getBoleto()));

        assertThat(boletoService.buscarBoletoPeloUuidAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")))
                .usingRecursiveComparison()
                .isEqualTo(getBoletoResponse());
    }

    @Test
    void test_buscar_boleto_pelo_uuid_associado_error() {
        when(boletoRepository.findByUuidAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> boletoService.buscarBoletoPeloUuidAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")));
    }

    @Test
    void test_pagar_boleto_ok(){
        when(boletoRepository.findByPagamentoBoleto(any(), anyFloat(), anyString()))
                .thenReturn(Optional.of(getBoleto()));

        when(boletoRepository.save(any()))
                .thenReturn(getBoleto());

        assertThat(boletoService.pagarBoleto(getPagamentoRequest()))
                .usingRecursiveComparison()
                .isEqualTo(getBoletoResponsePago());
    }

    @Test
    void test_pagar_boleto_error(){
        when(boletoRepository.findByPagamentoBoleto(any(), anyFloat(), anyString()))
                .thenReturn(Optional.empty());

        when(boletoRepository.save(any()))
                .thenReturn(getBoleto());

        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> boletoService.pagarBoleto(getPagamentoRequest()));
    }

    @Test
    void test_pagar_boleto_error_situacao_paga(){
        when(boletoRepository.findByPagamentoBoleto(any(), anyFloat(), anyString()))
                .thenReturn(Optional.of(getBoletoPago()));

        assertThatExceptionOfType(ApiRequestBadRequest.class)
                .isThrownBy(() -> boletoService.pagarBoleto(getPagamentoRequest()));
    }

    @Test
    void test_pagar_boleto_error_valor_invalido(){
        when(boletoRepository.findByPagamentoBoleto(any(), anyFloat(), anyString()))
                .thenReturn(Optional.of(getBoleto()));

        assertThatExceptionOfType(ApiRequestBadRequest.class)
                .isThrownBy(() -> boletoService.pagarBoleto(getPagamentoRequestValorInvalido()));
    }
}
