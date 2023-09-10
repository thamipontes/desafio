package com.desafio.arquivo.listener;

import com.desafio.arquivo.dtos.BoletoResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class ArquivoListener {
    @RabbitListener(queues = "boletos.v1.boleto-pago")
    public void emBoletoCriado(BoletoResponse boletoResponse) {
        gerarArquivo(boletoResponse);
    }

    public void gerarArquivo(BoletoResponse boletoResponse) {
        String nomeArquivo = "arquivo/arquivosgerados/" + boletoResponse.getId() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            String documentoPagador = String.format("%014d", Long.parseLong(boletoResponse.getDocumentoPagador()));
            String valorPago = String.format("%020d", (int) (boletoResponse.getValor() * 100));
            String linha = documentoPagador + boletoResponse.getId() + valorPago;
            writer.write(linha);
            writer.newLine();
            System.out.println("Arquivo gerado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo: " + e.getMessage());
        }
    }
}
