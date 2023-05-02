package com.helpCenter.notificationsEmails.mailSenderService;

import com.helpCenter.requestHandlers.entity.RequestHandler;

public interface EmailSenderService {

	void sendEmailForIncident(String[] toEmail, String title, String description);

	void sendEmailAfterCommentCreation(String[] toEmails, String comment, String incidentTitle);

	void sendMailOnStatusUpdate(String email, String title, String status);
	
	void sendMailOnCategoryCreation(String email,String category);
	void sendMailOnCategoryUpdation(String [] emails,String categoryName,String code, int etaOfCategory,RequestHandler requestHandler);

}
