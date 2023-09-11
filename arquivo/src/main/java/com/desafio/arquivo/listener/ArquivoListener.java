package com.desafio.arquivo.listener;

import com.desafio.arquivo.dtos.BoletoResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Log4j2
public class ArquivoListener {
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void onBoletoCriado(BoletoResponse boletoResponse) throws IOException {
        gerarArquivo(boletoResponse);
    }

    public void gerarArquivo(BoletoResponse boletoResponse) throws IOException {
        String nomeArquivo = "arquivos/arquivosgerados/" + boletoResponse.getId() + ".txt";

        Path diretorio = Path.of("arquivos/arquivosgerados/");
        Files.createDirectories(diretorio);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            String documentoPagador = String.format("%014d", Long.parseLong(boletoResponse.getDocumentoPagador()));
            String valorPago = String.format("%020d", (int) (boletoResponse.getValor() * 100));
            String linha = documentoPagador + boletoResponse.getId() + valorPago;
            writer.write(linha);
            writer.newLine();
            log.info("Arquivo gerado com sucesso!");
        } catch (IOException e) {
            log.error("Erro ao gerar o arquivo: " + e.getMessage());
        }
    }
}
