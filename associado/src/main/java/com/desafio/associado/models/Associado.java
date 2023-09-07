package com.desafio.associado.models;

import com.desafio.associado.enuns.ETipoPessoa;
import com.desafio.associado.provider.AssociadoGroupSequenceProvider;
import com.desafio.associado.provider.CnpjGroup;
import com.desafio.associado.provider.CpfGroup;
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
import org.hibernate.validator.group.GroupSequenceProvider;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@GroupSequenceProvider(AssociadoGroupSequenceProvider.class)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "documento"}) })
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(max=14)
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    @NotBlank
    private String documento;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ETipoPessoa tipoPessoa;

    @Size(max=50)
    @NotBlank
    private String nome;
}
