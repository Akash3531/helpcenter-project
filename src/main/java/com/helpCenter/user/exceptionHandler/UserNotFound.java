package com.helpCenter.user.exceptionHandler;

public class UserNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String userName;

	public UserNotFound(String userName) {
		super(String.format("User not found with name: %s", userName));
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
