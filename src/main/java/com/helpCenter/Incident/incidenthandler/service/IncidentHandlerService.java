package com.helpCenter.Incident.incidenthandler.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.incidenthandler.Entity.IncidentHandler;

@Service
public interface IncidentHandlerService {

	void saveIncidentHandler(Incident incident);

	void getIncidentHandler(Incident incident);

	IncidentHandler getHandlerByStatus();

	void updateHandlerOnEtaExpiration(List<Incident> incidents);
}
