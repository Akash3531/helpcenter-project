package com.helpCenter.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpCenter.user.dto.RequestUserDTO;
import com.helpCenter.user.dto.ResponseUserDto;
import com.helpCenter.user.dto.ResponseUsersNameDto;
import com.helpCenter.user.dto.UpdateUserDto;
import com.helpCenter.user.serviceImpl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UsersController {

	@Autowired
	UserServiceImpl userServiceImpl;

// CREATE ADMIN USER
	@PostMapping("/admin")
	public ResponseEntity<?> createAdmin(@Valid @RequestBody RequestUserDTO requestUserDTO) {
		userServiceImpl.createAdmin(requestUserDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

// CREATE NON ADMIN
	@PostMapping("/")
	public ResponseEntity<?> Create(@Valid @RequestBody RequestUserDTO userDto) {
		userServiceImpl.createUser(userDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

// UPDATE USER FIELDS
	@PatchMapping("/{userName}")
	public ResponseEntity<?> updateUser(@Valid @PathVariable String userName,
			@RequestBody UpdateUserDto updateUserDto) {
		userServiceImpl.updateUser(userName, updateUserDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

// GET USER BY NAME
	@GetMapping("/{userName}")
	public ResponseEntity<ResponseUserDto> userByName(@PathVariable String userName) {
		ResponseUserDto userByName = userServiceImpl.userByName(userName);
		return new ResponseEntity<ResponseUserDto>(userByName, HttpStatus.OK);
	}

// GET LIST OF USERS NAME
	@GetMapping("/usersName")
	public ResponseEntity<List<ResponseUsersNameDto>> usersName() {
		List<ResponseUsersNameDto> usersName = userServiceImpl.usersName();
		return new ResponseEntity<List<ResponseUsersNameDto>>(usersName, HttpStatus.OK);
	}

// GET ALL USERS
	@GetMapping("/")
	public ResponseEntity<List<ResponseUserDto>> getAllUsers() {
		List<ResponseUserDto> allUser = userServiceImpl.allUser();

		return new ResponseEntity<List<ResponseUserDto>>(allUser, HttpStatus.OK);
	}

// DELETE USER
	@DeleteMapping("/{userName}")
	public ResponseEntity<?> deleteUser(@PathVariable String userName) {
		userServiceImpl.deleteUser(userName);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
