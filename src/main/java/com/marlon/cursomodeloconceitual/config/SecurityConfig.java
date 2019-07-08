package com.marlon.cursomodeloconceitual.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	// liberado
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"
	};
	
	// liberado, so vai poder recuperar os dados
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**"
	};
	
	@Override                //do framework
	protected void configure(HttpSecurity http) throws Exception{
		
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) { // se nos profiles ativos tiver contido o test, então vai liberar o acesso
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable(); // ativando o cors e desativando o csrf
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // meotodo so para pegar dados, não permitindo alterar
		.antMatchers(PUBLIC_MATCHERS).permitAll() // toda autenticação feita em public_matchers é permitida
		.anyRequest().authenticated(); // para todo o resto, se exige autenticação
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // para asegura que o back end não vai criar a sessão de usuario
	}
	
	// permitindo acesso básico de multiplas fontes para todos os caminhos, configurações básicas
	@Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	  }
}