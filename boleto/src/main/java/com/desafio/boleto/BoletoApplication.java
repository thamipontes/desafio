package com.desafio.boleto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BoletoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoletoApplication.class, args);
	}

}
