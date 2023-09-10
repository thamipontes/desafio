package com.desafio.arquivo;

import com.desafio.arquivo.dtos.BoletoResponse;
import com.desafio.arquivo.listener.ArquivoListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ArquivoListenerTest {
    @Autowired
    private ArquivoListener arquivoListener;
    @Test
    public void test_gerar_arquivo() throws IOException {
        BoletoResponse boletoResponse = new BoletoResponse();
        boletoResponse.setId("12345Test");
        boletoResponse.setDocumentoPagador("12345678901234");
        boletoResponse.setValor(100.50f);

        Path diretorio = Path.of("arquivos/arquivosgerados/");
        Files.createDirectories(diretorio);

        arquivoListener.gerarArquivo(boletoResponse);

        String nomeArquivo = "arquivos/arquivosgerados/" + boletoResponse.getId() + ".txt";
        String conteudoArquivo = lerConteudoArquivo(nomeArquivo);

        String documentoPagador = String.format("%014d", Long.parseLong(boletoResponse.getDocumentoPagador()));
        String valorPago = String.format("%020d", (int) (boletoResponse.getValor() * 100));
        String linhaEsperada = documentoPagador + boletoResponse.getId() + valorPago;
        assertEquals(linhaEsperada, conteudoArquivo);
    }

    private String lerConteudoArquivo(String nomeArquivo) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                conteudo.append(linha);
            }
        }
        return conteudo.toString();
    }
}
