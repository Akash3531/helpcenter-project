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
		mailSender.setHost("10.8.14.41");
		mailSender.setPort(2525);
		mailSender.setUsername("jbawa@seasia.in");
		mailSender.setPassword("SinghSahib#7512");
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.starttls.enable", false);
		
		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
	
//	@Bean
//	public JavaMailSender mailSender()
//	{
//		JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
//		mailSender.setHost("smtp.gmail.com");
//		mailSender.setPort(587);
//		mailSender.setUsername("dingramail000@gmail.com");
//		mailSender.setPassword("dzdxtgzccoelxueo");
//		
//		Properties javaMailProperties = new Properties();
//		javaMailProperties.put("mail.smtp.auth", true);
//		javaMailProperties.put("mail.smtp.starttls.enable", true);
//		
//		mailSender.setJavaMailProperties(javaMailProperties);
//		return mailSender;
//	}
}
