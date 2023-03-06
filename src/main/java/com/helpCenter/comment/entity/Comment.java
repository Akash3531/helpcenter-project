package com.helpCenter.comment.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.helpCenter.Incident.entity.ImageCreation;
import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.comment.dto.RequestCommentDto;
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

@Component
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@CreatedDate
	private Date createdDate;

	private String comments;

	@JsonManagedReference
	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ImageCreation> images = new ArrayList<>();

	@ManyToOne
	private Incident incident;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<ImageCreation> getImages() {
		return images;
	}

	public void setImages(List<ImageCreation> images) {
		this.images = images;
	}

	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", createdDate=" + createdDate + ", comments=" + comments + ", images=" + images
				+ ", incident=" + incident + "]";
	}

	// Dto Conversion : RequestDto To CommentsClass
	public Comment dtoToClass(RequestCommentDto commentDto) {
		Comment comments = new Comment();
		comments.setComments(commentDto.getComments());
		return comments;
	}

}
