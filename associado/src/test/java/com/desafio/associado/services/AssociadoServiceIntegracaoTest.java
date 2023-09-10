package com.desafio.associado.services;

import com.desafio.associado.dtos.AssociadoResponse;
import com.desafio.associado.enuns.ETipoPessoa;
import com.desafio.associado.exceptions.ApiRequestBadRequest;
import com.desafio.associado.exceptions.ApiRequestNotFound;
import com.desafio.associado.models.Associado;
import com.desafio.associado.repositories.AssociadoRepository;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.desafio.associado.helpers.AssociadoHelpers.getAssociadoCnpj;
import static com.desafio.associado.helpers.AssociadoHelpers.getAssociadoCpf;
import static com.desafio.associado.helpers.AssociadoHelpers.getAssociadoRequestCnpj;
import static com.desafio.associado.helpers.AssociadoHelpers.getAssociadoRequestCpf;
import static com.desafio.associado.helpers.AssociadoHelpers.getAssociadoRequestErrorCnpj;
import static com.desafio.associado.helpers.AssociadoHelpers.getAssociadoRequestErrorCpf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AssociadoServiceIntegracaoTest {
    @Autowired
    AssociadoService associadoService;

    @Autowired
    AssociadoRepository associadoRepository;

    private MockWebServer mockWebServer;


    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8082);
    }

    @AfterEach
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void test_criar_associado_ok_cpf() {
        AssociadoResponse associadoResponse = associadoService.criarAssociado(getAssociadoRequestCpf());

        assertThat(associadoResponse).extracting(AssociadoResponse::getDocumento,
                        AssociadoResponse::getTipoPessoa, AssociadoResponse::getNome)
                .contains("81775744000", ETipoPessoa.PF, "João da Silva");
    }

    @Test
    void test_criar_associado_ok_cnpj() {
        AssociadoResponse associadoResponse = associadoService.criarAssociado(getAssociadoRequestCnpj());
        assertThat(associadoResponse).extracting(AssociadoResponse::getDocumento,
                        AssociadoResponse::getTipoPessoa, AssociadoResponse::getNome)
                .contains("95730385000159", ETipoPessoa.PJ, "João da Silva");
    }

    @Test
    void test_criar_associado_error_cpf() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> associadoService.criarAssociado(getAssociadoRequestErrorCpf()));

    }

    @Test
    void test_criar_associado_error_cnpj() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> associadoService.criarAssociado(getAssociadoRequestErrorCnpj()));

    }

    @Test
    void test_buscar_associado_cpf(){
        Associado associado = associadoRepository.save(getAssociadoCpf());
        AssociadoResponse associadoResponse = associadoService.buscarAssociado("81775744000");

        assertThat(associadoResponse).extracting(AssociadoResponse::getId, AssociadoResponse::getDocumento,
                        AssociadoResponse::getTipoPessoa, AssociadoResponse::getNome)
                .contains(associado.getId(), "81775744000", ETipoPessoa.PF, "João da Silva");
    }

    @Test
    void test_buscar_associado_cnpj(){
        Associado associado = associadoRepository.save(getAssociadoCnpj());
        AssociadoResponse associadoResponse = associadoService.buscarAssociado("95730385000159");

        assertThat(associadoResponse).extracting(AssociadoResponse::getId, AssociadoResponse::getDocumento,
                        AssociadoResponse::getTipoPessoa, AssociadoResponse::getNome)
                .contains(associado.getId().toString(), "95730385000159", ETipoPessoa.PJ, "João da Silva");
    }

    @Test
    void test_buscar_associado_error_cpf() {
        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> associadoService.buscarAssociado("81775744000"));

    }

    @Test
    void test_buscar_associado_error_cnpj() {
        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> associadoService.buscarAssociado("95730385000159"));

    }

    @Test
    void test_buscar_associado_por_id_cpf(){
        Associado associado = associadoRepository.save(getAssociadoCpf());

        AssociadoResponse associadoResponse = associadoService.buscarAssociadoPorId(associado.getId());

        assertThat(associadoResponse).extracting(AssociadoResponse::getId,
                AssociadoResponse::getDocumento,
                AssociadoResponse::getNome,
                AssociadoResponse::getTipoPessoa).contains(associado.getId().toString(),
                        "81775744000",
                        "João da Silva",
                        ETipoPessoa.PF);
    }

    @Test
    void test_buscar_associado_por_id_cnpj(){
        Associado associado = associadoRepository.save(getAssociadoCnpj());

        AssociadoResponse associadoResponse = associadoService.buscarAssociadoPorId(associado.getId());

        assertThat(associadoResponse).extracting(AssociadoResponse::getId,
                AssociadoResponse::getDocumento,
                AssociadoResponse::getNome,
                AssociadoResponse::getTipoPessoa).contains(associado.getId().toString(),
                "95730385000159",
                "João da Silva",
                ETipoPessoa.PJ);
    }

    @Test
    void test_buscar_associado_por_id_error_cpf() {
        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> associadoService.buscarAssociadoPorId(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")));

    }

    @Test
    void test_buscar_associado_por_id_error_cnpj() {
        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> associadoService.buscarAssociadoPorId(UUID.fromString("e9b6ddd8-c1af-47c6-ad66-560a89c0282f")));

    }

    @Test
    void test_atualizar_associado_cpf(){
        Associado associado = associadoRepository.save(getAssociadoCpf());
        AssociadoResponse associadoResponse = associadoService.atualizarAssociado(associado.getId(), getAssociadoRequestCpf());

        assertThat(associadoResponse).extracting(AssociadoResponse::getId,
                AssociadoResponse::getDocumento,
                AssociadoResponse::getNome,
                AssociadoResponse::getTipoPessoa).contains(associado.getId().toString(),
                "81775744000",
                "João da Silva",
                ETipoPessoa.PF);
    }

    @Test
    void test_atualizar_associado_cnpj(){
        Associado associado = associadoRepository.save(getAssociadoCnpj());
        AssociadoResponse associadoResponse = associadoService.atualizarAssociado(associado.getId(), getAssociadoRequestCnpj());

        assertThat(associadoResponse).extracting(AssociadoResponse::getId,
                AssociadoResponse::getDocumento,
                AssociadoResponse::getNome,
                AssociadoResponse::getTipoPessoa).contains(associado.getId().toString(),
                "95730385000159",
                "João da Silva",
                ETipoPessoa.PJ);
    }

    @Test
    void test_atualizar_associado_error_cpf() {
        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> associadoService.atualizarAssociado(UUID.fromString("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"), getAssociadoRequestCpf()));

    }

    @Test
    void test_atualizar_associado_error_cnpj() {
        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> associadoService.atualizarAssociado(UUID.fromString("e9b6ddd8-c1af-47c6-ad66-560a89c0282f"), getAssociadoRequestCnpj()));

    }

    @Test
    void test_deletar_associado_cpf_pago(){
        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(getJson("boleto_response_pago.json"));

        mockWebServer.enqueue(response);


        associadoService.deletarAssociado(UUID.fromString("6c916bd0-a318-411c-ba9f-16e463d7a2b3"));
        assertEquals(0, associadoRepository.count());
    }

    @Test
    void test_deletar_associado_error_nao_pago(){
        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(getJson("boleto_response_nao_pago.json"));

        mockWebServer.enqueue(response);


        assertThatExceptionOfType(ApiRequestBadRequest.class)
                .isThrownBy(() -> associadoService.deletarAssociado(UUID.fromString("5225be08-4f00-11ee-be56-0242ac120002")));
    }

    @Test
    void test_deletar_associado_cpf_error_nao_achar_associado(){
        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockWebServer.enqueue(response);

        assertThatExceptionOfType(ApiRequestNotFound.class)
                .isThrownBy(() -> associadoService.deletarAssociado(UUID.fromString("5225be08-4f00-11ee-be56-0242ac120002")));

    }

    @Test
    void test_deletar_associado_cpf_error(){
        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        mockWebServer.enqueue(response);

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> associadoService.deletarAssociado(UUID.fromString("5225b408-4f00-11ee-be56-0242ac120002")));

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
