package com.nineleaps.supplier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class EmailConfig {

	@Bean
	public SimpleMailMessage emailTemplate() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("vkstream@hotmail.com");
		message.setFrom("vivekrathi74@gmail.com");
		message.setSubject("Order placed by Nineleaps");
		message.setText(" Yo bro mail is working perfectly.");
		return message;
	}

}
