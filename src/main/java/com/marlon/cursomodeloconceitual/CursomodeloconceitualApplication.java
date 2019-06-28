package com.marlon.cursomodeloconceitual;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomodeloconceitualApplication implements CommandLineRunner {

	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomodeloconceitualApplication.class, args);
	}

	@Override // metodo de injeção
	public void run(String... args) throws Exception {
	}

}
