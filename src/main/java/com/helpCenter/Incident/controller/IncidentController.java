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

import com.helpCenter.Incident.dtos.GetIncidentbyCategory;
import com.helpCenter.Incident.dtos.RequestIncidentDto;
import com.helpCenter.Incident.dtos.ResponseIncidentDto;
import com.helpCenter.Incident.dtos.UpdateIncidentDto;
import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.service.IncidentService;
import com.helpCenter.elasticSearch.aggregators.AggregationResponse;
import com.helpCenter.elasticSearch.aggregators.IntervalDataResponseAggregation;

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
	@PostMapping(path = "/", consumes = { org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> createIncident(@RequestParam(value = "image", required = false) List<MultipartFile> file,
			@Valid @RequestPart(value = "incident") RequestIncidentDto incidentdto) throws Exception {

		incidentService.createIncident(incidentdto, file);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

// UPDATE INCIDENT
	@PatchMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> updateIncident(@PathVariable int id,
			@RequestParam(value = "Image", required = false) List<MultipartFile> file,
			@RequestPart(value = "incident") UpdateIncidentDto incidentdto) throws IOException {

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

//Get INCIDENT BY USER ID	
	@GetMapping("/byuser/{user_id}")
	public ResponseEntity<List<GetIncidentbyCategory>> getIncidentbyUser(@PathVariable int user_id,
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		List<GetIncidentbyCategory> incidents = incidentService.getIncidentbyUser(user_id, pageNumber, pageSize);
		return new ResponseEntity<List<GetIncidentbyCategory>>(incidents, HttpStatus.OK);
	}

//Get INCIDENT BY CATEGORY CODE	
	@GetMapping("/bycode/{code}")
	public ResponseEntity<List<GetIncidentbyCategory>> getIncidentbyCategoryCode(@PathVariable String code,
			@RequestParam(value = "pageNumber",defaultValue ="1", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize) {
		List<GetIncidentbyCategory> incidents = incidentService.getIncidentbyCategoryCode(code, pageNumber, pageSize);
		return new ResponseEntity<List<GetIncidentbyCategory>>(incidents, HttpStatus.OK);
	}
 
// Get Incidents From Elastic Search
	@GetMapping("/elastic/{index}/{text}")
    public List<ResponseIncidentDto> getDataList(@PathVariable String index,@PathVariable String text) {
		List<ResponseIncidentDto> incidents = incidentService.getAllIncidentsFromElastic(index,text);
        return incidents;
    }

// Get BucketList From Elastic Search
	@GetMapping("/elastic/bucket/{index}/{field}")
    public List<AggregationResponse> getBucketList(@PathVariable String index,@PathVariable String field) {
      
		List<AggregationResponse> incidents = incidentService.getBucketListFromElastic(index,field);
        return incidents;
    }
	
	@GetMapping("/elastic/interval/{index}/{interval}")
	public List<IntervalDataResponseAggregation> getInterval(@PathVariable String index,@PathVariable String interval){
		
		List<IntervalDataResponseAggregation> resp=incidentService.getIntervalDataFromElastic(index, interval);
		return resp;
	}
	
	@GetMapping("/elastics/{index}")
	public List<ResponseIncidentDto> getDatabYDateTimeWithElastic(@PathVariable String index,@RequestParam(value="start",required = true) long startDate,@RequestParam(value="end",required = true) long endDate){
		
		List<ResponseIncidentDto> response=incidentService.getDataBydateAndTimeFromElastic(index, startDate, endDate);
		return response;
		
	}
	
	
	
	
}
