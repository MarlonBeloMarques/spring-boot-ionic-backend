package com.marlon.cursomodeloconceitual.services;

import org.springframework.mail.SimpleMailMessage;

import com.marlon.cursomodeloconceitual.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
