package com.helpCenter.notificationsEmails.mailSenderServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.helpCenter.Incident.serviceImpl.IncidentServiceImpl;
import com.helpCenter.notificationsEmails.mailSenderService.EmailSenderService;

import jakarta.mail.internet.MimeMessage;

@Component
public class MailSenderServiceImpl implements EmailSenderService {

	@Autowired
	JavaMailSender mailSender;
	

	@Override
	public void sendEmailForIncident(String[] toEmail, String title, String description) {
		try {

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo("springtest@yopmail.com");
			helper.setSubject(title);
			helper.setText(description);
			mailSender.send(mimeMessage);
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendEmailAfterCommentCreation(String[] toEmails, String comment, String incidentTitle) {
		try {
			MimeMessage mimeMessage=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
			helper.setTo("springtest@yopmail.com");
			helper.setSubject(incidentTitle);
			helper.setText(comment);
			mailSender.send(mimeMessage);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}


}
