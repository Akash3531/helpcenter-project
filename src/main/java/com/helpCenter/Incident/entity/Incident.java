package com.helpCenter.Incident.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.helpCenter.Incident.dtos.RequestIncidentDto;
import com.helpCenter.Incident.dtos.UpdateIncidentDto;
import com.helpCenter.category.entity.Category;
import com.helpCenter.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Component
public class Incident {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String title;

	private String description;

	private String categoryCode;

	private String status = "In Progress";

	@CreatedDate
	private Date createdDate;

	private Date lastmailSendedTime;
	private String priority;

//	@JsonManagedReference(value = "category")
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@JsonManagedReference(value = "image")
	@OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ImageCreation> images = new ArrayList<>();

	@JsonManagedReference(value = "user")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getLastmailSendedTime() {
		return lastmailSendedTime;
	}

	public void setLastmailSendedTime(Date lastmailSendedTime) {
		this.lastmailSendedTime = lastmailSendedTime;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Incident(int id, String title, String description, String categoryCode, String status, Date createdDate,
			Date lastmailSendedTime, Category category) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.categoryCode = categoryCode;
		this.status = status;
		this.createdDate = createdDate;
		this.lastmailSendedTime = lastmailSendedTime;
		this.category = category;
	}

	public Incident() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "incidentCreation [id=" + id + ", title=" + title + ", description=" + description + ", categoryCode="
				+ categoryCode + ", Status=" + status + ", priority=" + priority + ", category=" + category
				+ ", images=" + images + ", user=" + user + "]";
	}

// Conversion Method Request_Incident_Dto to Incident
	public Incident DtoToIncident(RequestIncidentDto incidentDto) {
		Incident incident = new Incident();
		incident.setTitle(incidentDto.getTitle());
		incident.setDescription(incidentDto.getDescription());
		incident.setCategoryCode(incidentDto.getCategoryCode());
		incident.setLastmailSendedTime(incidentDto.getLastmailSendedTime());
		incident.setPriority(incidentDto.getPriority());
		return incident;

	}

	// Conversion Method Update_Incident_Dto to Incident
	public Incident UpdateDtoToIncident(UpdateIncidentDto incidentDto) {
		Incident incident = new Incident();
		incident.setTitle(incidentDto.getTitle());
		incident.setStatus(incidentDto.getStatus());
		incident.setDescription(incidentDto.getDescription());
		incident.setCategoryCode(incidentDto.getCategoryCode());
		incident.setLastmailSendedTime(incidentDto.getLastmailSendedTime());
		incident.setPriority(incidentDto.getPriority());
		return incident;

	}

}
