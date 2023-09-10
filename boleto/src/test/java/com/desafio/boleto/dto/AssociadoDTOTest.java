package com.desafio.boleto.dto;

import com.desafio.boleto.client.associado.dto.AssociadoDTO;
import com.desafio.boleto.utils.ValidacaoExcecao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AssociadoDTO.class)
public class AssociadoDTOTest {
    @Test
    void test_setters_associado_dto(){
        AssociadoDTO associadoDTO = new AssociadoDTO();
        associadoDTO.setId("1");
        associadoDTO.setDocumento("12345678910");
        associadoDTO.setTipoPessoa("PF");
        associadoDTO.setNome("Teste");

        assertEquals("1", associadoDTO.getId());
        assertEquals("12345678910", associadoDTO.getDocumento());
        assertEquals("PF", associadoDTO.getTipoPessoa());
        assertEquals("Teste", associadoDTO.getNome());
    }
}
