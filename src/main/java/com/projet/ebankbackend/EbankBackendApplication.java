package com.projet.ebankbackend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EbankBackendApplication 
{

	public static void main(String[] args) {
		SpringApplication.run(EbankBackendApplication.class, args);
	}
}
