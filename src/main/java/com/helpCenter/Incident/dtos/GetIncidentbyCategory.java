package com.helpCenter.Incident.dtos;

import org.springframework.stereotype.Component;

import com.helpCenter.Incident.entity.Incident;

@Component
public class GetIncidentbyCategory {
	private int id;

	private String title;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

//Conversion 
	public  GetIncidentbyCategory UserIncident(Incident incident) {
		 GetIncidentbyCategory dto = new  GetIncidentbyCategory();
		dto.setId(incident.getId());
		dto.setTitle(incident.getTitle());
		return dto;

	}
}
