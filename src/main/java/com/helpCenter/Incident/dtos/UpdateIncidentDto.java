package com.helpCenter.Incident.dtos;

import java.util.Date;

public class UpdateIncidentDto {

	private String title;
	private String description;
	private String categoryCode;
	private String status;
	private String priority;

	private Date lastmailSendedTime;
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

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getLastmailSendedTime() {
		return lastmailSendedTime;
	}

	public void setLastmailSendedTime(Date date) {
		this.lastmailSendedTime = date;
	}

	public UpdateIncidentDto() {
		super();
		
	}
	
}
