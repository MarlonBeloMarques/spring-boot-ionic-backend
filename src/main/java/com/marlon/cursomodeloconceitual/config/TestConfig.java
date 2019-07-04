// configrações especificas do profile de teste

package com.marlon.cursomodeloconceitual.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.marlon.cursomodeloconceitual.services.DBService;
import com.marlon.cursomodeloconceitual.services.EmailService;
import com.marlon.cursomodeloconceitual.services.MockEmailService;

// tudo que estiver dentro da classe so será ativado quando o profile de teste for true
@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean // vai esta disponivel como componente no sistema
	public EmailService emailService() {
		return new MockEmailService();
	}
	
}
