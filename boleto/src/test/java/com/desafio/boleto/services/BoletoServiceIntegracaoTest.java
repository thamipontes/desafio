package com.desafio.boleto.services;

import com.desafio.boleto.dtos.BoletoResponse;
import com.desafio.boleto.dtos.PagamentoBoletoRequest;
import com.desafio.boleto.enuns.ESituacaoBoleto;
import com.desafio.boleto.exceptions.ApiRequestIntegrationException;
import com.desafio.boleto.exceptions.ApiRequestNotFound;
import com.desafio.boleto.models.Boleto;
import com.desafio.boleto.repositories.BoletoRepository;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.RabbitMQContainer;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.desafio.boleto.helpers.BoletoHelper.getBoleto;
import static com.desafio.boleto.helpers.BoletoHelper.getBoletoRequest;
import static com.desafio.boleto.helpers.BoletoHelper.getBoletoRequestError;
import static com.desafio.boleto.helpers.BoletoHelper.getPagamentoRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class BoletoServiceIntegracaoTest {

    @Autowired
    BoletoService boletoService;

    @Autowired
    BoletoRepository boletoRepository;

    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8081);
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void test_criar_boleto() {
        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(getJson("associado_response.json"));
        mockWebServer.enqueue(response);

        BoletoResponse responseBoleto = boletoService.criarBoleto(getBoletoRequest());

        assertThat(responseBoleto).extracting(BoletoResponse::getNomePagador, BoletoResponse::getSituacaoBoleto,
                        BoletoResponse::getDocumentoPagador, BoletoResponse::getValor)
                .contains("Nome do Pagador", ESituacaoBoleto.NAO_PAGO, "91468816039", 1000.0f);
    }

    @Test
    public void test_criar_boleto_error_sem_associado() {
        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockWebServer.enqueue(response);

        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> boletoService.criarBoleto(getBoletoRequest()));
    }

    @Test
    public void test_criar_boleto_error() {
        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(getJson("associado_response.json"));
        mockWebServer.enqueue(response);

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> boletoService.criarBoleto(getBoletoRequestError()));
    }

    @Test
    void test_buscar_boleto_pelo_uuid_associado() {
        Boleto boleto = boletoRepository.save(getBoleto());
        BoletoResponse boletoResponse = boletoService.buscarBoletoPeloUuidAssociado(boleto.getUuidAssociado());

        assertThat(boletoResponse)
                .extracting(BoletoResponse::getUuidAssociado,
                        BoletoResponse::getDocumentoPagador,
                        BoletoResponse::getNomePagador,
                        BoletoResponse::getNomeFantasiaPagador,
                        BoletoResponse::getValor,
                        BoletoResponse::getVencimento,
                        BoletoResponse::getSituacaoBoleto)
                .contains(boleto.getUuidAssociado().toString(),
                        boleto.getDocumentoPagador(),
                        boleto.getNomePagador(),
                        boleto.getNomeFantasiaPagador(),
                        boleto.getValor(),
                        boleto.getVencimento(),
                        boleto.getSituacaoBoleto());
    }

        @Test
        void test_buscar_boleto_pelo_uuid_associado_error() {
            assertThatExceptionOfType(ApiRequestNotFound.class)
                    .isThrownBy(() -> boletoService.buscarBoletoPeloUuidAssociado(UUID.fromString("052a0588-7fa3-4324-8877-734c1b187564")));

        }

    @Test
    void test_pagar_boleto(){
        Boleto boleto = boletoRepository.save(getBoleto());
        BoletoResponse boletoResponse = boletoService.pagarBoleto(new PagamentoBoletoRequest(boleto.getId().toString(),
                boleto.getValor(), boleto.getDocumentoPagador()));

        assertThat(boletoResponse)
                .extracting(BoletoResponse::getUuidAssociado,
                        BoletoResponse::getDocumentoPagador,
                        BoletoResponse::getNomePagador,
                        BoletoResponse::getNomeFantasiaPagador,
                        BoletoResponse::getValor,
                        BoletoResponse::getVencimento,
                        BoletoResponse::getSituacaoBoleto)
                .contains(boleto.getUuidAssociado().toString(),
                        boleto.getDocumentoPagador(),
                        boleto.getNomePagador(),
                        boleto.getNomeFantasiaPagador(),
                        boleto.getValor(),
                        boleto.getVencimento(),
                        ESituacaoBoleto.PAGO);
    }

    @Test
    void test_pagar_boleto_error() {
        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> boletoService.pagarBoleto(getPagamentoRequest()));

    }

    private String getJson(String path) {
        try {
            InputStream jsonStream = this.getClass().getClassLoader().getResourceAsStream(path);
            assert jsonStream != null;
            return new String(jsonStream.readAllBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
