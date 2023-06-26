package com.helpCenter.Incident.sheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.reposatiory.IncidentReposatiory;

@Component
public class ShedulerForIncidentUpdate {

	@Autowired
	IncidentReposatiory incidentReposatiory;

	@Scheduled(fixedDelay = 60000)
	public void runForCategoryEtaInValidation() {
		List<Incident> incidents = incidentReposatiory.findAll();// get all saved incident
		for (Incident incident : incidents) {
			String status = incident.getStatus();// getting status of incident
			if (status.contentEquals("inValidation")) {
				int eta = incident.getEtaInValidation();
				long etaInMillis = eta * 60 * 1000;
				long lastUpdatedTime = incident.getUpDatedDate().getTime();
				if (lastUpdatedTime + etaInMillis < System.currentTimeMillis()) {
					incident.setStatus("closed");
					incidentReposatiory.save(incident);
				}

			}

		}
	}
}
