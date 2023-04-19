package com.helpCenter.aspects;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.reposatiory.IncidentReposatiory;

@Component
@Aspect
public class QueueNumberAspect {

	@Autowired
	IncidentReposatiory incidentReposatiory;

	@AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.createIncident(..))", returning = "Incident")
	public void get_QueueNumberOn_IncidetnCreation(Incident Incident) {
		List<Integer> ids = new ArrayList<>();
		String categoryCode = Incident.getCategory().getCode();
		int id = Incident.getId();
		List<Incident> byCategoryCodeAndStatus = incidentReposatiory.findIncidentByCategoryCodeAndStatus(categoryCode);
		for (Incident incident2 : byCategoryCodeAndStatus) {
			int id2 = incident2.getId();
			ids.add(id2);
		}
		int indexOf = ids.indexOf(id);
	}

	@AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.updateIncident(..))", returning = "incident")
	public void get_queueNumberOn_StatusUpdate(Incident incident) {
		List<Incident> byCategoryCodeAndStatus = incidentReposatiory
				.findIncidentByCategoryCodeAndStatus(incident.getCategoryCode());
		for (Incident incident2 : byCategoryCodeAndStatus) {
			int id = incident2.getId();
		}
	}
}
