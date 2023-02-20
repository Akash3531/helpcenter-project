package com.helpCenter.Incident.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.helpCenter.Incident.dtos.RequestIncidentDto;
import com.helpCenter.Incident.dtos.ResponseIncidentDto;
import com.helpCenter.Incident.dtos.UpdateIncidentDto;
import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.service.IncidentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/incident")
public class IncidentController {

	@Autowired
	IncidentService incidentService;
	@Autowired
	Incident incident;
	@Autowired
	ResponseIncidentDto getIncidentDto;

// CREATE INCIDENT
	@PostMapping(path = "/", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> createIncident(@RequestParam(value = "Image", required = false) List<MultipartFile> file,
		@Valid	@RequestPart(value = "incident") RequestIncidentDto incidentdto) throws IOException {

		incidentService.createIncident(incidentdto, file);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

// UPDATE INCIDENT
	@PatchMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> updateIncident(@PathVariable int id,
			@RequestParam(value = "Image", required = false) List<MultipartFile> file,
			@RequestPart(value = "incident", required = false) UpdateIncidentDto incidentdto) throws IOException {

		incidentService.updateIncident(id, incidentdto, file);
		return new ResponseEntity<>(HttpStatus.OK);
	}

// GET ALL INCIDENTS
	@GetMapping("/")
	public ResponseEntity<List<ResponseIncidentDto>> getAllIncidents() {
		List<ResponseIncidentDto> incidents = incidentService.getAllIncidents();
		return new ResponseEntity<List<ResponseIncidentDto>>(incidents, HttpStatus.OK);
	}

// GET INCIDENT BY INCIDENT-ID
	@GetMapping("/{id}")
	public ResponseEntity<ResponseIncidentDto> getIncidentByid(@PathVariable int id) {
		ResponseIncidentDto incident = incidentService.getIncidentById(id);
		return new ResponseEntity<ResponseIncidentDto>(incident, HttpStatus.OK);
	}

}
