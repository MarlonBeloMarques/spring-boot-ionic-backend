package com.marlon.cursomodeloconceitual.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.marlon.cursomodeloconceitual.security.JWTAuthenticationFilter;
import com.marlon.cursomodeloconceitual.security.JWTAuthorizationFilter;
import com.marlon.cursomodeloconceitual.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // permiti inserir preautorização de endpoints
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// instancia
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	// liberado
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"
	};
	
	// liberado, so vai poder recuperar os dados
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
			"/estados/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/clientes",
			"/auth/forgot/**"
	};
	
	@Override                //do framework
	protected void configure(HttpSecurity http) throws Exception{
		
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) { // se nos profiles ativos tiver contido o test, então vai liberar o acesso
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable(); // ativando o cors e desativando o csrf
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // meotodo so para pegar dados, não permitindo alterar
		.antMatchers(PUBLIC_MATCHERS).permitAll() // toda autenticação feita em public_matchers é permitida
		.anyRequest().authenticated(); // para todo o resto, se exige autenticação
		//registrando o filtro                                            
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil)); // authenticationManager ja é um metodo disponivel da classe
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // para asegura que o back end não vai criar a sessão de usuario
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPassowrdEncoder());
	}
	
	// permitindo acesso básico de multiplas fontes para todos os caminhos, configurações básicas
	@Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "PUT", "DELETE", "GET", "OPTIONS"));
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	  }
	
	// criptografia de senha
	@Bean 
	public BCryptPasswordEncoder bCryptPassowrdEncoder() {
		return new BCryptPasswordEncoder();
	}
}
