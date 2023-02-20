package com.helpCenter.Incident.dtos;

import jakarta.validation.constraints.NotNull;

public class RequestIncidentDto {

	private String title;

	private String description;

	@NotNull
	private String categoryCode;

	private String priority;

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

	public RequestIncidentDto() {
		super();
		
	}	
 
}
