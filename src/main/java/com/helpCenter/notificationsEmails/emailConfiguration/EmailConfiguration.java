package com.helpCenter.notificationsEmails.emailConfiguration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost("some.com");
		mailSender.setPort(000);
		mailSender.setUsername("someone@gmail.com");
		mailSender.setPassword("password");
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.starttls.enable", false);

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}

}
