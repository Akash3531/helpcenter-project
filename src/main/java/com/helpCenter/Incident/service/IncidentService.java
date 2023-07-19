package com.helpCenter.Incident.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.helpCenter.Incident.dtos.GetIncidentbyCategory;
import com.helpCenter.Incident.dtos.RequestIncidentDto;
import com.helpCenter.Incident.dtos.ResponseIncidentDto;
import com.helpCenter.Incident.dtos.UpdateIncidentDto;
import com.helpCenter.Incident.entity.Incident;

public interface IncidentService {

	Incident createIncident(RequestIncidentDto incident, List<MultipartFile> file) throws Exception;

	List<ResponseIncidentDto> getAllIncidents();

	ResponseIncidentDto getIncidentById(int id);

	Incident updateIncident(int id, UpdateIncidentDto incident, List<MultipartFile> file) throws IOException;

    List<GetIncidentbyCategory> getIncidentbyUser(int user_id,Integer pageNumber , Integer pageSize);
	 
    List<GetIncidentbyCategory> getIncidentbyCategoryCode(String code,Integer pageNumber , Integer pageSize);
	
    List<ResponseIncidentDto> getAllIncidentsFromElastic(String index,String text);
    
  
}
