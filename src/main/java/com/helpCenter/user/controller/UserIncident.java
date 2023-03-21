package com.helpCenter.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpCenter.Incident.dtos.ResponseIncidentDto;
import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.service.IncidentService;
import com.helpCenter.user.dto.ResponseUserDto;
import com.helpCenter.user.serviceImpl.UserServiceImpl;

@RestController
public class UserIncident {

	@Autowired
	IncidentService incidentService;
	@Autowired
	Incident incident;
	@Autowired
	UserServiceImpl userServiceImpl;
	
	
	


}
