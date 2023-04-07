package com.helpCenter.notificationsEmails.mailSenderService;

public interface EmailSenderService {

	void sendEmailForIncident(String[] toEmail, String title, String description);

	void sendEmailAfterCommentCreation(String[] toEmails, String comment, String incidentTitle);

	void sendMailOnStatusUpdate(String email, String title, String status);
	
	void sendMailOnCategoryCreation(String email,String category);

}
