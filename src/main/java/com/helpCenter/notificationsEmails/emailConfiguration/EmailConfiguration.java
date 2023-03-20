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
		mailSender.setHost("smtp.com");
		mailSender.setPort(000);
		mailSender.setUsername("your mail address");
		mailSender.setPassword("your password");
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.ssl.enable", false);
		
		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
}
