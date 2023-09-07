package com.desafio.boleto.models;

import com.desafio.boleto.enuns.ESituacaoBoleto;
import com.desafio.boleto.provider.CnpjGroup;
import com.desafio.boleto.provider.CpfGroup;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "id", "uuidAssociado"}) })
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Float valor;
    private UUID uuidAssociado;
    private String vencimento;

    @Size(max=14)
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    @NotBlank
    private String documentoPagador;

    @Size(max=50)
    @NotBlank
    private String nomePagador;

    @Size(max=50)
    private String nomeFantasiaPagador;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ESituacaoBoleto situacaoBoleto;


}
