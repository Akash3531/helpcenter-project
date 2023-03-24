package com.helpCenter.comment.dto;

public class RequestCommentDto {

	private String comments;

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public RequestCommentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestCommentDto(String comments) {
		super();
		this.comments = comments;
	}

}
