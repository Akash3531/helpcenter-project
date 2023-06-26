package com.helpCenter.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UpdateUserDto {
	@NotEmpty
	@Pattern(message = "Minimum 2 characters requried", regexp = "^(?=.{2,20}$)(?![_.])(?!.*[_.]{2})[A-Za-z._]+(?<![_.])$")
	private String userName;
	private String password;
	private String department;

	public UpdateUserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpdateUserDto(String userName, String password, String department) {
		this.userName = userName;
		this.password = password;
		this.department = department;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
