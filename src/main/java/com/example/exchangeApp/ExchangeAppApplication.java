package com.example.exchangeApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ExchangeAppApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ExchangeAppApplication.class, args);
	}

}
