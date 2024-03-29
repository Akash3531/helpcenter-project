package com.helpCenter.notificationsEmails.mailSenderServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.helpCenter.notificationsEmails.mailSenderService.EmailSenderService;

import jakarta.mail.MessagingException;
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
			helper.setFrom("jbawa@seasia.in");
			helper.setTo("springtest@yopmail.com");
			helper.setSubject("Regarding ||" + title);
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
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom("jbawa@seasia.in");
			helper.setTo("springtest@yopmail.com");
			helper.setSubject("Comment ||" + incidentTitle);
			helper.setText(comment);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendEmailAfterStatusUpdate(String email, String title, String status) {

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom("jbawa@seasia.in");
			helper.setTo(email);
			helper.setSubject("status updation");
			helper.setText(
					"your incident staus is:" + status.toUpperCase() + " " + "for incident:" + title.toUpperCase());
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
