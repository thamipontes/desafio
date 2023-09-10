package com.desafio.associado.controllers;


import com.desafio.associado.dtos.AssociadoRequest;
import com.desafio.associado.dtos.AssociadoResponse;
import com.desafio.associado.services.AssociadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("associados")
public class AssociadoController {
    @Autowired
    private final AssociadoService associadoService;
    @Operation(summary = "Criação de um associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar esse recurso."),
            @ApiResponse(responseCode = "500", description = "A exceção foi gerada."),
    })
    @PostMapping
    public ResponseEntity<AssociadoResponse> criarAssociado(@RequestBody AssociadoRequest associadoRequest){
        AssociadoResponse response = associadoService.criarAssociado(associadoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar um associado pelo documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar esse recurso."),
            @ApiResponse(responseCode = "500", description = "A exceção foi gerada."),
    })
    @GetMapping
    public ResponseEntity<AssociadoResponse> buscarAssociado(@RequestParam String documento){
        AssociadoResponse response = associadoService.buscarAssociado(documento);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar um associado pelo UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar esse recurso."),
            @ApiResponse(responseCode = "500", description = "A exceção foi gerada."),
    })
    @GetMapping("{uuid}")
    public ResponseEntity<AssociadoResponse> buscarAssociaoPorId(@PathVariable String uuid){
        UUID uuidConvert = UUID.fromString(uuid);
        AssociadoResponse response = associadoService.buscarAssociadoPorId(uuidConvert);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar um associado pelo uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar esse recurso."),
            @ApiResponse(responseCode = "500", description = "A exceção foi gerada."),
    })
    @PatchMapping("{uuid}")
    public ResponseEntity<AssociadoResponse> atualizarAssociado(@PathVariable String uuid, @RequestBody AssociadoRequest associadoRequest){
        UUID uuidConvert = UUID.fromString(uuid);
        AssociadoResponse response = associadoService.atualizarAssociado(uuidConvert, associadoRequest);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Deletar um associado pelo uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar esse recurso."),
            @ApiResponse(responseCode = "500", description = "A exceção foi gerada."),
    })
    @DeleteMapping("{uuid}")
    public ResponseEntity<?> deletarAssociado(@PathVariable String uuid){
        UUID uuidConvert = UUID.fromString(uuid);
        associadoService.deletarAssociado(uuidConvert);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
