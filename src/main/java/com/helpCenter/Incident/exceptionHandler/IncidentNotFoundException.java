package com.helpCenter.Incident.exceptionHandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncidentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int id;
	
	private String code;

	public IncidentNotFoundException(int id) {
		super(String.format("Incident Not Found With Incident_id :%s", id));
		this.id = id;

	}
	
	public IncidentNotFoundException(String code) {
		super(String.format("Incident Not Found With Incident_id :%s", code));
		this.code=code;

	}
}
