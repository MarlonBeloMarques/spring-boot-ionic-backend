// configrações especificas do profile de teste

package com.marlon.cursomodeloconceitual.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.marlon.cursomodeloconceitual.services.DBService;

// tudo que estiver dentro da classe so será ativado quando o profile de teste for true
@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		
		if(!"create".equals(strategy))
			return false;
		
		dbService.instantiateTestDatabase();
		return true;
	}
}
