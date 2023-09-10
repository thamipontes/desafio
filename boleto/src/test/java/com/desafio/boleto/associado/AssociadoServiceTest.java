package com.desafio.boleto.associado;

import com.desafio.boleto.client.associado.AssociadoClient;
import com.desafio.boleto.client.associado.service.AssociadoService;
import com.desafio.boleto.client.associado.service.impl.AssociadoServiceImpl;
import com.desafio.boleto.exceptions.ApiRequestIntegrationException;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.desafio.boleto.helpers.BoletoHelper.getAssociado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        AssociadoServiceImpl.class
})
public class AssociadoServiceTest {
    @Autowired
    AssociadoService associadoService;

    @MockBean
    AssociadoClient associadoClient;

    @Test
    void test_buscar_associado_ok() {

        when(associadoClient.buscarAssociado("12345678910"))
                .thenReturn(getAssociado());

        assertThat(associadoService.buscarAssociado("12345678910"))
                .usingRecursiveComparison()
                .isEqualTo(getAssociado());
    }

    @Test
    void test_buscar_associado_error() {
        when(associadoClient.buscarAssociado("12345678910"))
                .thenThrow(IntegrationException.class);

        assertThatExceptionOfType(ApiRequestIntegrationException.class)
                .isThrownBy(() -> associadoService.buscarAssociado("12345678910"));
    }
}
