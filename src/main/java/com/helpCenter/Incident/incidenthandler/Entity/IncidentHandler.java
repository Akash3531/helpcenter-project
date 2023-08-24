package com.helpCenter.Incident.incidenthandler.Entity;

import java.util.List;

import com.helpCenter.Incident.entity.Incident;

import jakarta.persistence.Convert;
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

	@Convert(converter = StringListConverter.class)
	private List<String> name;

	@ManyToOne
	@JoinColumn(name = "incident_id")
	private Incident incident;

	private String Status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Incident getIncident() {
		return incident;
	}

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public IncidentHandler(int id, List<String> name, Incident incident) {
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
