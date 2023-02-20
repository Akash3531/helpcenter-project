package com.helpCenter.user.exceptionHandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyExist extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String userName;

	public UserAlreadyExist(String userName) {
		super(String.format("User already exist with name :%s", userName));
		this.userName = userName;
	}

}
