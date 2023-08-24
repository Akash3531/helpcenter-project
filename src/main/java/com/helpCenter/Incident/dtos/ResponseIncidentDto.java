package com.helpCenter.Incident.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;
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
	
	private String status;

	private List<ImageCreation> images = new ArrayList<>();

	private String userName;
	
	@CreatedDate
	private Date createdDate;

	private String userEmail;

	private String userDepartment;

	private Category category;
	
	
	

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

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
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		dto.setCreatedDate(incident.getCreatedDate());
		dto.setTitle(incident.getTitle());
		dto.setStatus(incident.getStatus());
		dto.setCategory(incident.getCategory());
		return dto;

	}

// Conversion for show id and title
	public ResponseIncidentDto UserIncident(Incident incident) {
		ResponseIncidentDto dto = new ResponseIncidentDto();
		dto.setId(incident.getId());
		dto.setTitle(incident.getTitle());
		return dto;

	}

// conversion for elastic
	@SuppressWarnings("unchecked")
	public ResponseIncidentDto convertToIncident(Map<String, Object> hit) {
		Map<String, Object> source = (Map<String, Object>) hit.get("_source");
		Map<String, Object> user = (Map<String, Object>) source.get("user");
		Integer id = null;
		Object idValue = source.get("id");
		if (idValue != null) {
			id = (Integer) idValue;
		}
		String title = (String) source.get("title");
		String description = (String) source.get("description");
		String categoryCode = (String) source.get("categoryCode");
		String priority = (String) source.get("priority");
		String status =(String) source.get("status");
		String userName = (String) user.get("userName");
		String userEmail = (String) user.get("userEmail");
		long createdDate= (long)source.get("createdDate");	
		String userDepartment = (String) user.get("userDepartment");
		List<ImageCreation> images = (List<ImageCreation>) source.get("images");

		ResponseIncidentDto incident = new ResponseIncidentDto();
		incident.setId(id);
		incident.setTitle(title);
		incident.setDescription(description);
		incident.setImages(images);
		incident.setStatus(status);
		incident.setCategoryCode(categoryCode);
		incident.setPriority(priority);
		incident.setUserName(userName);		
		incident.setCreatedDate(new Date(createdDate));	
		incident.setUserDepartment(userDepartment);
		incident.setUserEmail(userEmail);
		return incident;
	}
	
	 
}
