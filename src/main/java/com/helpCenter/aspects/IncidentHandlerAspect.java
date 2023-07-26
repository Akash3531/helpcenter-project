package com.helpCenter.aspects;

import java.util.List;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.incidenthandler.service.IncidentHandlerService;

@Aspect
@Component
public class IncidentHandlerAspect {

	@Autowired
	IncidentHandlerService incidentHandlerService;

	// store incident handler after incident creation
	@AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.createIncident(..))", returning = "incident")
	public void assignIncidentHandlerWithIncident(Incident incident) {

		incidentHandlerService.saveIncidentHandler(incident);

	}

	@AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.updateIncident(..))", returning = "incident")
	public void updateIncident(Incident incident) {

		String status = incident.getStatus();
		if (status.equals("ongoing")) {

			incidentHandlerService.getIncidentHandler(incident);
		}

	}
	
	//Call incident handler when eta is expire
	@AfterReturning(pointcut = "execution(* com.helpCenter.notificationsEmails.scheduler.SchedulerForCategoryEtaExpiration.runForCategoryEta(..))", returning = "incidents")
	public void updateHandlerOnEtaExpiration(List<Incident> incidents)
	{
		if(incidents!=null)
		{
		incidentHandlerService.updateHandlerOnEtaExpiration(incidents);
		}
	}
}
