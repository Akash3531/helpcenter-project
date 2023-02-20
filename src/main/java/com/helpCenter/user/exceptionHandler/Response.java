package com.helpCenter.user.exceptionHandler;

import lombok.ToString;

@ToString
public class Response {

	private String Message;

	public Response(String message) {
		super();
		this.Message = message;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		this.Message = message;
	}

	public Response() {
		// TODO Auto-generated constructor stub
	}

}
