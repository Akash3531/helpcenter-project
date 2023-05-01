package com.helpCenter.aspects;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.admin.NewTopic;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.reposatiory.IncidentReposatiory;
import com.helpCenter.kafkaSetUp.model.Message;

@Component
@Aspect
public class QueueNumberAspect {
	@Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;
    @Autowired
    NewTopic  topic;
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
		String message = "Your ticket number is" + Incident.getId() + "your incident is" + indexOf
				+ " in the queue.Your incident will be resolve shortly";
		 
	}

	@AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.updateIncident(..))", returning = "incident")
	public void get_queueNumberOn_StatusUpdate(Incident incident) {
		if (incident.getStatus() != null && !incident.getStatus().equals("ToDo")) {
			List<Integer> listOfIds = new ArrayList<>();
			List<Incident> byCategoryCodeAndStatus = incidentReposatiory
					.findIncidentByCategoryCodeAndStatus(incident.getCategoryCode());
			for (Incident incident2 : byCategoryCodeAndStatus) {
				listOfIds.add(incident2.getId());
			}
			for (Integer no : listOfIds) {

				int indexOf = listOfIds.indexOf(no);
				// Incident incidentById = incidentReposatiory.incidentById(no);

			}
		}
	}
}
