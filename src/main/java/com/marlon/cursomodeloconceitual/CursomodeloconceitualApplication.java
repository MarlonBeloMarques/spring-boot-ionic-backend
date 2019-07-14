package com.marlon.cursomodeloconceitual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.marlon.cursomodeloconceitual.services.S3Service;

@SpringBootApplication
public class CursomodeloconceitualApplication implements CommandLineRunner {

	@Autowired
	private S3Service s3Service;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomodeloconceitualApplication.class, args);
	}

	@Override // metodo de injeção
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\MARLON BELO MARQUES\\Pictures\\ippo.png");
	}

}
