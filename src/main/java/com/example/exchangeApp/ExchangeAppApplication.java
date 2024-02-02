package com.example.exchangeApp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.exchangeApp.model.Currency;


@EnableScheduling
@SpringBootApplication
public class ExchangeAppApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ExchangeAppApplication.class, args);
	}

}
