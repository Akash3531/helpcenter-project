package com.helpCenter.notificationsEmails.service;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.comment.entity.Comment;

public interface InformationProviderForEmailService {

	void getIncidentCategoryDetails(Incident incident);
	void getCommentDetails(Comment comment);
	void getUserDetailAfterStatusUpdate(Incident updateIncident);
	
}
