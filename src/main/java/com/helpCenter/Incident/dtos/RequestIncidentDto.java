package com.helpCenter.Incident.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotNull;

public class RequestIncidentDto {

	private String title;

	private String description;

	@NotNull
	private String categoryCode;
	private Date lastmailSendedTime;
	private String status;
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

	public Date getLastmailSendedTime() {
		return lastmailSendedTime;
	}

	public void setLastmailSendedTime(Date lastmailSendedTime) {
		this.lastmailSendedTime = lastmailSendedTime;
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

	public RequestIncidentDto() {
		super();

	}

}
