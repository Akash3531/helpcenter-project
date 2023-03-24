package com.helpCenter.comment.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.helpCenter.Incident.entity.ImageCreation;
import com.helpCenter.comment.entity.Comment;
import com.helpCenter.user.entity.User;

@Component
public class CommentByIncidentIdDto {

	private int id;

	private String comments;

	private List<ImageCreation> images = new ArrayList<>();

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

	public CommentByIncidentIdDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CommentByIncidentIdDto [id=" + id + ", comments=" + comments + ", images=" + images + "]";
	}

	// Dto conversion
	public CommentByIncidentIdDto dtoConversion(Comment comment) {
		CommentByIncidentIdDto dto = new CommentByIncidentIdDto();
		dto.setId(comment.getId());
		dto.setComments(comment.getComments());
		dto.setImages(comment.getImages());
        dto.setUser(comment.getUser());
		return dto;
	}

}
