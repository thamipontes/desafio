package com.desafio.boleto.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverteData.class)
public class ConverteDataTest {
    @Test
    void test_convert_data() {
        assertThat(ConverteData.converterData("01-01-2021")).isEqualTo("2021-01-01");
    }
}
