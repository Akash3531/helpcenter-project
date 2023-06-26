package com.helpCenter.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.helpCenter.user.dto.RequestUserDTO;
import com.helpCenter.user.dto.ResponseUserDto;
import com.helpCenter.user.dto.ResponseUsersNameDto;
import com.helpCenter.user.dto.UpdateUserDto;

@Service
public interface UserService {

	void createUser(RequestUserDTO userDto);

	void updateUser(String userName, UpdateUserDto updateUserDto);

	ResponseUserDto userByName(String userName);

	List<ResponseUserDto> allUser();

	void deleteUser(String userName);

	List<ResponseUsersNameDto> usersName();

	void assignTicket(int Ticketid , String User);
}
