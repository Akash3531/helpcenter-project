package com.helpCenter.Incident.incidenthandler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.helpCenter.Incident.incidenthandler.service.IncidentHandlerService;

@RestController
public class IncidentHandlerController {

	@Autowired
	IncidentHandlerService incidentHandlerService;

}
