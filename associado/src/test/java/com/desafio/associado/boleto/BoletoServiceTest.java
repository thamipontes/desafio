package com.desafio.associado.boleto;

import com.desafio.associado.clients.boleto.BoletoClient;
import com.desafio.associado.clients.boleto.services.BoletoService;
import com.desafio.associado.clients.boleto.services.impl.BoletoServiceImpl;
import com.desafio.associado.exceptions.ApiRequestIntegrationException;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.desafio.associado.helpers.AssociadoHelpers.getBoletoDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        BoletoServiceImpl.class
})
public class BoletoServiceTest {
    @Autowired
    BoletoService boletoService;

    @MockBean
    BoletoClient boletoClient;

    @Test
    void test_buscar_boleto_pelo_uuid_associado_ok() {
        when(boletoClient.buscarBoletoPeloUuidAssociado("a9b6ddd8-c1af-47c6-ad66-560a89c0281f")).thenReturn(getBoletoDTO());
        assertThat(boletoService.buscarBoletoPeloUuidAssociado("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"))
                .usingRecursiveComparison()
                .isEqualTo(getBoletoDTO());
    }

    @Test
    void test_buscar_boleto_pelo_uuid_associado_error() {
        when(boletoClient.buscarBoletoPeloUuidAssociado(any())).thenThrow(IntegrationException.class);

        assertThatExceptionOfType(ApiRequestIntegrationException.class)
                .isThrownBy(() -> boletoService.buscarBoletoPeloUuidAssociado("a9b6ddd8-c1af-47c6-ad66-560a89c0281f"));
    }
}
