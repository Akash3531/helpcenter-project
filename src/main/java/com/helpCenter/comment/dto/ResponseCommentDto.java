package com.helpCenter.comment.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.helpCenter.Incident.entity.ImageCreation;
import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.comment.entity.Comment;

@Component
public class ResponseCommentDto {

	private int id;

	private String comments;

	private List<ImageCreation> images = new ArrayList<>();

	private Incident incident;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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


// Dto Conversion Commentclass To Dto
	public ResponseCommentDto commentToResponsedto(Comment comment) {
		ResponseCommentDto dto = new ResponseCommentDto();
		dto.setId(comment.getId());
		dto.setComments(comment.getComments());
		dto.setImages(comment.getImages());
		dto.setIncident(comment.getIncident());
		return dto;
	}
}
