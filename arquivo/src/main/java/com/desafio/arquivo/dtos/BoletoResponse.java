package com.desafio.arquivo.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BoletoResponse implements Serializable {

    private String id;
    private Float valor;
    private String uuidAssociado;
    private String vencimento;
    private String documentoPagador;
    private String nomePagador;
    private String nomeFantasiaPagador;
    private String situacaoBoleto;
}
