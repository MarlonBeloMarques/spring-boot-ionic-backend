package com.marlon.cursomodeloconceitual.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender; // automaticamente vai instanciar com todos os dados do email passado em propreties
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class); // Logger referente ha essa classe
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		mailSender.send(msg);
		LOG.info("Email enviado");
		
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando email HTML...");
		javaMailSender.send(msg);
		LOG.info("Email enviado");
		
	}

}
