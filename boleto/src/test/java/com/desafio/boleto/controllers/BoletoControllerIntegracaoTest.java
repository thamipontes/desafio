package com.desafio.boleto.controllers;

import com.desafio.boleto.dtos.BoletoResponse;
import com.desafio.boleto.dtos.PagamentoBoletoRequest;
import com.desafio.boleto.services.BoletoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

import static com.desafio.boleto.helpers.BoletoHelper.getBoletoRequest;
import static com.desafio.boleto.helpers.BoletoHelper.getBoletoResponseJson;
import static com.desafio.boleto.helpers.BoletoHelper.getBoletoResponseJsonPago;
import static com.desafio.boleto.helpers.BoletoHelper.getPagamentoRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class BoletoControllerIntegracaoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoletoService boletoService;

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
    public void test_criar_boleto() throws Exception {

        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(getJson("associado_response.json"));
        mockWebServer.enqueue(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(getBoletoRequest());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/boletos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));


        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.situacaoBoleto").value("NAO_PAGO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuidAssociado").value("052a0588-7fa3-4324-8877-734c1b187564"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value("1000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documentoPagador").value("91468816039"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomePagador").value("Nome do Pagador"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeFantasiaPagador").value("Nome Fantasia do Pagador"));

    }

    @Test
    public void test_buscar_boleto_pelo_uuid_associado() throws Exception {
        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(getJson("associado_response.json"));
        mockWebServer.enqueue(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(getBoletoRequest());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/boletos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/boletos")
                .param("uuidAssociado", "052a0588-7fa3-4324-8877-734c1b187564")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.situacaoBoleto").value("NAO_PAGO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value("1000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuidAssociado").value("052a0588-7fa3-4324-8877-734c1b187564"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documentoPagador").value("91468816039"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomePagador").value("Nome do Pagador"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeFantasiaPagador").value("Nome Fantasia do Pagador"));

    }

    @Test
    public void test_pagar() throws Exception {
        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(getJson("associado_response.json"));
        mockWebServer.enqueue(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(getBoletoRequest());

        ResultActions resultPost = mockMvc.perform(MockMvcRequestBuilders
                .post("/boletos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        String boletoResponseJson = resultPost.andReturn().getResponse().getContentAsString();
        BoletoResponse boletoResponse = objectMapper.readValue(boletoResponseJson, BoletoResponse.class);

        String jsonPagamento = objectMapper.writeValueAsString(new PagamentoBoletoRequest(boletoResponse.getId(),
                boletoResponse.getValor(), boletoResponse.getDocumentoPagador()));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/boletos/pagamento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPagamento));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.situacaoBoleto").value("PAGO"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value("1000.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(boletoResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuidAssociado").value("052a0588-7fa3-4324-8877-734c1b187564"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vencimento").value(boletoResponse.getVencimento()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documentoPagador").value("91468816039"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomePagador").value("Nome do Pagador"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeFantasiaPagador").value("Nome Fantasia do Pagador"));
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