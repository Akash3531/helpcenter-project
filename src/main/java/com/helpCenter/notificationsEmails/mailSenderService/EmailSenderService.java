package com.helpCenter.notificationsEmails.mailSenderService;

public interface EmailSenderService {

	void sendEmailForIncident(String[] toEmail, String title, String description);
	void sendEmailAfterCommentCreation(String [] toEmails,String comment,String incidentTitle);
	void sendEmailAfterStatusUpdate(String email, String title);
}
