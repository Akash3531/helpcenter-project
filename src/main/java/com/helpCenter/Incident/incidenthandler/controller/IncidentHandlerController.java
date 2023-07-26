package com.helpCenter.Incident.incidenthandler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.helpCenter.Incident.incidenthandler.Entity.IncidentHandler;
import com.helpCenter.Incident.incidenthandler.service.IncidentHandlerService;

@RestController
public class IncidentHandlerController {

	@Autowired
	IncidentHandlerService incidentHandlerService;
	
	public ResponseEntity<IncidentHandler> getHandlers()
	{
	  return null;
	}
}
