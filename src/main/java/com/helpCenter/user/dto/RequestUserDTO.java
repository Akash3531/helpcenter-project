package com.helpCenter.user.dto;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import com.helpCenter.user.entity.Role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class RequestUserDTO {
	@NotEmpty
	@Pattern(message = "Minimum 2 characters requried", regexp = "^(?=.{2,20}$)(?![_.])(?!.*[_.]{2})[A-Za-z._]+(?<![_.])$")
	private String userName;
	private String password;
	private String email;
	private int failureAttempes = 0;
	private boolean active = false;
	private String createdBy;
	@CreatedDate
	private Date createdDate;
	
	private Role role;

	public RequestUserDTO() {
		super();

	}

	public RequestUserDTO(String userName, String password,String email, int failureAttempes, boolean active, String createdBy,
			Date createdDate,Role role) {
		super();
		this.userName = userName;
		this.password = password;
		this.email=email;
		this.failureAttempes = failureAttempes;
		this.active = active;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.role = role;
	}

	public RequestUserDTO(String userName, String password, String createdBy) {
		super();
		this.userName = userName;
		this.password = password;
		this.createdBy = createdBy;

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getFailureAttempes() {
		return failureAttempes;
	}

	public void setFailureAttempes(int failureAttempes) {
		this.failureAttempes = failureAttempes;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
