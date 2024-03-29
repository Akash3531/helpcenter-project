package com.helpCenter.notificationsEmails.informationProviderService;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.comment.entity.Comment;

public interface InformationProviderForEmailService {

	void getIncidentCategoryDetails(Incident incident);

	void getCommentDetails(Comment comment);

	void getDetailOfStatusUpdate(Incident updateIncident);

}
