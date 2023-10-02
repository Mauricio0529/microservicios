package com.microservicio.serviceproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceProductApplication {

	/*
	* Se esta trabajando con BD H2: es una base de datos en memoria
	* */

	public static void main(String[] args) {
		SpringApplication.run(ServiceProductApplication.class, args);
	}

}