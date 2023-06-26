package com.helpCenter.Incident.dtos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.helpCenter.Incident.entity.ImageCreation;
import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.category.entity.Category;

@Component
public class ResponseIncidentDto {

	private int id;

	private String title;

	private String description;

	private String categoryCode;

	private String priority;

	private List<ImageCreation> images = new ArrayList<>();

	private String userName;

	private String userEmail;

	private String userDepartment;

	private Category category;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public List<ImageCreation> getImages() {
		return images;
	}

	public void setImages(List<ImageCreation> images) {
		this.images = images;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserDepartment() {
		return userDepartment;
	}

	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public ResponseIncidentDto() {
		super();
	}

// DTO CONVERSION FROM INCIDENT TO INCIDENT_DTO
	public ResponseIncidentDto incidentToIncident_Dto(Incident incident) {
		ResponseIncidentDto dto = new ResponseIncidentDto();
		dto.setId(incident.getId());
		dto.setCategoryCode(incident.getCategoryCode());
		dto.setDescription(incident.getDescription());
		dto.setImages(incident.getImages());
		dto.setPriority(incident.getPriority());
		dto.setTitle(incident.getTitle());
		dto.setCategory(incident.getCategory());
		dto.setUserName(incident.getUser().getUsername());
		dto.setUserEmail(incident.getUser().getEmail());
		dto.setUserDepartment(incident.getUser().getDepartment());
		return dto;

	}

// Conversion for show id and title
	public ResponseIncidentDto UserIncident(Incident incident) {
		ResponseIncidentDto dto = new ResponseIncidentDto();
		dto.setId(incident.getId());
		dto.setTitle(incident.getTitle());
		return dto;

	}

}
