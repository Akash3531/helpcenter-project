package com.helpCenter.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.comment.entity.Comment;
import com.helpCenter.notificationsEmails.informationProviderServiceImpl.InformationProviderForEmailServiceImpl;

@Aspect
@Component
public class EmailAspect {

	@Autowired
	InformationProviderForEmailServiceImpl providerForEmailServiceImpl;
	
	//Call information after incident creation
	@AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.createIncident(..))", returning = "Incident")
	public void sentMailAfterCreatingIncident(Incident Incident) {
		if (Incident != null) {
			providerForEmailServiceImpl.getIncidentCategoryDetails(Incident);
		}
	}

	//Call information provider after comment creation
	@AfterReturning(pointcut = "execution(* com.helpCenter.comment.serviceImpl.CommentServiceimpl.createComment(..))", returning = "comment")
	public void sentMailAfterComment(Comment comment) {
		if (comment != null) {
			providerForEmailServiceImpl.getCommentDetails(comment);
		}
	}
	//Call information provider after incident status update
	@AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.updateIncident(..))", returning = "incident")
	public void sentMailAfterUpdateIncidentStatus(Incident incident) {
		if (incident.getStatus() != null && !incident.getStatus().equals("ToDo")) {
			providerForEmailServiceImpl.getDetailOfStatusUpdate(incident);
		}
	}
}
