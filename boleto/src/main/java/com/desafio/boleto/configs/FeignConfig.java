package com.desafio.boleto.configs;

import feign.Contract;
import feign.Retryer;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
public class FeignConfig {
    @Bean
    public Retryer retryer() {
        return new Retryer.Default(2000, 6000, 0);
    }

    @Bean
    public Contract contract() {
        return new SpringMvcContract();
    }


}