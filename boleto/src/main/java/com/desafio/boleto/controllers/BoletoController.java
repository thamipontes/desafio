package com.desafio.boleto.controllers;

import com.desafio.boleto.dtos.BoletoRequest;
import com.desafio.boleto.dtos.BoletoResponse;
import com.desafio.boleto.dtos.PagamentoBoletoRequest;
import com.desafio.boleto.services.BoletoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("boletos")
public class BoletoController {

    @Autowired
    private final BoletoService boletoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Operation(summary = "Criação de um boleto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar esse recurso."),
            @ApiResponse(responseCode = "500", description = "A exceção foi gerada."),
    })
    @PostMapping
    public ResponseEntity<BoletoResponse> criarBoleto(@RequestBody BoletoRequest boletoRequest){
        BoletoResponse response = boletoService.criarBoleto(boletoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar um boleto pelo uuid do associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar esse recurso."),
            @ApiResponse(responseCode = "500", description = "A exceção foi gerada."),
    })
    @GetMapping
    public ResponseEntity<BoletoResponse> buscarBoletoPeloUuidAssociado(@RequestParam String uuidAssociado){
        BoletoResponse response = boletoService.buscarBoletoPeloUuidAssociado(UUID.fromString(uuidAssociado));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Realizar o pagamento de um boleto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar esse recurso."),
            @ApiResponse(responseCode = "500", description = "A exceção foi gerada."),
    })
    @PostMapping("pagamento")
    public ResponseEntity<BoletoResponse> pagarBoleto(@RequestBody PagamentoBoletoRequest pagamentoBoletoRequest){
        BoletoResponse response = boletoService.pagarBoleto(pagamentoBoletoRequest);
        rabbitTemplate.convertAndSend("boletos.v1.boleto-pago", response);
        return ResponseEntity.ok(response);
    }

}
