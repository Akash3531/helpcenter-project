package com.helpCenter.user.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.helpCenter.user.entity.Role;
import com.helpCenter.user.entity.User;

@Component
public class ResponseUserDto {
	private String userName;
	private String createdBy;
	private Date createdDate;
	private List<Role> roles = new ArrayList<>();

	public ResponseUserDto() {

		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseUserDto(User user) {
		super();
		this.userName = user.getUsername();
		this.createdBy = user.getCreatedBy();
		this.createdDate = user.getCreatedDate();
		this.roles = user.getRole();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public ResponseUserDto userToDto(User user) {
		ResponseUserDto getUserDto = new ResponseUserDto();
		getUserDto.setUserName(user.getUsername());
		getUserDto.setCreatedBy(user.getCreatedBy());
		getUserDto.setCreatedDate(user.getCreatedDate());
		getUserDto.setRoles(user.getRole());

		return getUserDto;
	}

}
