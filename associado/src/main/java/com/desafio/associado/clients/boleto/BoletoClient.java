package com.desafio.associado.clients.boleto;

import com.desafio.associado.clients.boleto.dtos.BoletoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "boletoClient", url="${boleto.host.api}")
public interface BoletoClient {
    @GetMapping(path = "/boletos",
            consumes = "application/json",
            produces = "application/json")
    BoletoDTO buscarBoletoPeloUuidAssociado(@RequestParam String uuidAssociado);
}
