package com.helpCenter.notificationsEmails.emailConfiguration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

	@Bean
	public JavaMailSender mailSender()
	{
		JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
		mailSender.setHost("asmt.com");
		mailSender.setPort(00);
		mailSender.setUsername("abc.com");
		mailSender.setPassword("abc");
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.ssl.enable", false);
		
		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
}
