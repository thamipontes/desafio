package com.desafio.associado.controllers;

import com.desafio.associado.services.AssociadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

import static com.desafio.associado.helpers.AssociadoHelpers.getAssociadoRequest;
import static com.desafio.associado.helpers.AssociadoHelpers.getAssociadoRequestPut;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class AssociadoControllerIntegracaoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AssociadoService associadoService;

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
    public void test_criar_associado() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(getAssociadoRequest());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/associados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documento").value("81775744000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoPessoa").value("PF"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("João da Silva"));

    }

    @Test
    public void test_buscar_associado() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(getAssociadoRequest());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/associados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/associados")
                .param("documento", "81775744000")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documento").value("81775744000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoPessoa").value("PF"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("João da Silva"));

    }

    @Test
    public void test_buscar_associado_por_id() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(getAssociadoRequest());

        ResultActions resultPost = mockMvc.perform(MockMvcRequestBuilders
                .post("/associados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        String associadoResponseJson = resultPost.andReturn().getResponse().getContentAsString();
        String id = objectMapper.readTree(associadoResponseJson).get("id").asText();

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/associados/" + id)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documento").value("81775744000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoPessoa").value("PF"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("João da Silva"));

    }

    @Test
    void test_atualizar_associado() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(getAssociadoRequest());

        ResultActions resultPost = mockMvc.perform(MockMvcRequestBuilders
                .post("/associados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        String associadoResponseJson = resultPost.andReturn().getResponse().getContentAsString();
        String id = objectMapper.readTree(associadoResponseJson).get("id").asText();

        String jsonPut = objectMapper.writeValueAsString(getAssociadoRequestPut());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .patch("/associados/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPut));

        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.documento").value("81775744000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoPessoa").value("PF"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("João da Silva Carvalho"));

    }

    @Test
    public void test_deletar_associado() throws Exception {

        MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(getJson("boleto_response_pago.json"));

        mockWebServer.enqueue(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(getAssociadoRequest());

        ResultActions resultPost = mockMvc.perform(MockMvcRequestBuilders
                .post("/associados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        String associadoResponseJson = resultPost.andReturn().getResponse().getContentAsString();
        String id = objectMapper.readTree(associadoResponseJson).get("id").asText();

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/associados/" + id)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isNoContent());

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
