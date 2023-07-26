package com.helpCenter.notificationsEmails.mailSenderServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.helpCenter.notificationsEmails.mailSenderService.EmailSenderService;
import com.helpCenter.requestHandlers.entity.RequestHandler;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailSenderServiceImpl implements EmailSenderService {

	@Autowired
	JavaMailSender mailSender;

	//send mail on incident creation
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//send mail on comment creation
	@Override
	public void sendEmailAfterCommentCreation(String[] toEmails, String comment, String incidentTitle) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom("jbawa@seasia.in");
			helper.setTo("springtest@yopmail.com");
			helper.setSubject("Comment ||" + incidentTitle);
			helper.setSubject("Regarding ||" + incidentTitle);
			helper.setText(comment);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//send mail on status updation of a incident 
	@Override
	public void sendMailOnStatusUpdate(String email, String title, String status) {

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom("jbawa@seasia.in");
			helper.setTo("springtest@yopmail.com");
			helper.setSubject("Regarding || Status Updation");
			helper.setText(
					"your incident staus is:" + status.toUpperCase() + " " + "for incident:" + title.toUpperCase());
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}

	}
	//send mail on category creation
	@Override
	public void sendMailOnCategoryCreation(String email, String category) {

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setFrom("jbawa@seasia.in");
			helper.setTo("springtest@yopmail.com");
			helper.setSubject("Regarding || Category creation");
			helper.setText("There is a category created name as :"+category);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//send mail on category updation
	@Override
	public void sendMailOnCategoryUpdation(String[] emails, String categoryName,String code, int etaOfCategory,
			RequestHandler requestHandler) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setFrom("jbawa@seasia.in");
			helper.setTo("springtest@yopmail.com");
			helper.setSubject("Regarding || Category updation");
			helper.setText("category name is :"+categoryName.toUpperCase()
			+""+"is updated"+"\n"+"category details are:"+"\n"+"name:"+categoryName+" "+"code:"+code+" "+"eta:"+etaOfCategory+" "+"RequestHandler:"+requestHandler);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void sendEmailForIncidentAssign(String toEmail, String title, String description) {
		try {

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom("jbawa@seasia.in");
			helper.setTo("springtest@yopmail.com");
			helper.setSubject("Regarding ||" + title);
			helper.setText(description+" "+"Ticket Assign");
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
