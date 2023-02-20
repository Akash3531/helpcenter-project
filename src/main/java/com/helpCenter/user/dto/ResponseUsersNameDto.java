package com.helpCenter.user.dto;

import org.springframework.stereotype.Component;

import com.helpCenter.user.entity.User;

@Component
public class ResponseUsersNameDto {
	private String userName;
	
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public ResponseUsersNameDto usersName(User user)
	{
		ResponseUsersNameDto responseUsersNameDto=new ResponseUsersNameDto();
		responseUsersNameDto.setUserName(user.getUsername());
		 
		 return responseUsersNameDto;
	}
}
