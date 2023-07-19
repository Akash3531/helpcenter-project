package com.helpCenter.Incident.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class IncidentHandler {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name ="incident_id")
	private Incident incident;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	public IncidentHandler(int id, String name, Incident incident) {
		super();
		this.id = id;
		this.name = name;
		this.incident = incident;
	}

	public IncidentHandler() {
		super();
	
	}

	@Override
	public String toString() {
		return "IncidentHandler [id=" + id + ", name=" + name + ", incident=" + incident + "]";
	}
	
	
}
