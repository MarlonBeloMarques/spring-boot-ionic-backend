package com.marlon.cursomodeloconceitual.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marlon.cursomodeloconceitual.dto.EmailDTO;
import com.marlon.cursomodeloconceitual.security.JWTUtil;
import com.marlon.cursomodeloconceitual.security.UserSS;
import com.marlon.cursomodeloconceitual.services.AuthService;
import com.marlon.cursomodeloconceitual.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	// tem que ta logado para acessar
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		//liberar cabeçalho
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	// tem que ta logado para acessar. desc: esqueci minha senha
		@RequestMapping(value = "/forgot", method = RequestMethod.POST)
		public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
			service.sendNewPassword(objDto.getEmail());
			return ResponseEntity.noContent().build();
		}
}
