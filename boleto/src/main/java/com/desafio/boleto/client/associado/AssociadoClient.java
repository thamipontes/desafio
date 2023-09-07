package com.desafio.boleto.client.associado;

import com.desafio.boleto.client.associado.dto.AssociadoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "associadoClient", url="${associado.host.api}")
public interface AssociadoClient {
    @GetMapping(path = "/associados",
            consumes = "application/json",
            produces = "application/json")
    AssociadoDTO buscarAssociado(@RequestParam String documento);
}
