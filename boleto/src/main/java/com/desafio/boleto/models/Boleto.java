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

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "id", "uuidAssociado"}) })
public class Boleto {

    @Id
    private String id = generateCustomUUID();

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

    public static String generateCustomUUID() {
        byte[] randomBytes = new byte[12]; // 13 bytes = 104 bits
        new SecureRandom().nextBytes(randomBytes);

        // Defina a primeira variação de UUID (versão 0) e a variante do DCE 1.1 (RFC 4122)
        randomBytes[6] &= 0x0F;  // clear version
        randomBytes[6] |= 0x40;  // set version to 0100
        randomBytes[8] &= 0x3F;  // clear variant
        randomBytes[8] |= 0x80;  // set to IETF variant

        String base64String = Base64.getEncoder().encodeToString(randomBytes);

        // Remova os caracteres de preenchimento e os hífens (se houver)
        base64String = base64String
                .replace("=", "")
                .replace("/", "")
                .replace("-", "");

        // Se a string for maior que 13 caracteres, pegue os primeiros 13 caracteres
        if (base64String.length() >= 14) {
            base64String = base64String.substring(0, 14);
        }

        return base64String;
    }


}
