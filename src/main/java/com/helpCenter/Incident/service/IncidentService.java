package com.helpCenter.Incident.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.helpCenter.Incident.dtos.RequestIncidentDto;
import com.helpCenter.Incident.dtos.ResponseIncidentDto;
import com.helpCenter.Incident.dtos.UpdateIncidentDto;
import com.helpCenter.Incident.entity.Incident;

public interface IncidentService {

	void createIncident(RequestIncidentDto incident, List<MultipartFile> file) throws IOException;

	List<ResponseIncidentDto> getAllIncidents();

	ResponseIncidentDto getIncidentById(int id);

	void updateIncident(int id, UpdateIncidentDto incident, List<MultipartFile> file) throws IOException;

	public List<ResponseIncidentDto> getIncidentbyUser(int user_id);
}
