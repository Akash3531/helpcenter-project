package com.helpCenter.user.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.helpCenter.user.dto.RequestUserDTO;
import com.helpCenter.user.dto.ResponseUserDto;
import com.helpCenter.user.dto.ResponseUsersNameDto;
import com.helpCenter.user.dto.UpdateUserDto;
import com.helpCenter.user.entity.Constants;
import com.helpCenter.user.entity.Role;
import com.helpCenter.user.entity.User;
import com.helpCenter.user.exceptionHandler.UserAlreadyExist;
import com.helpCenter.user.exceptionHandler.UserNotFound;
import com.helpCenter.user.repository.RoleRepository;
import com.helpCenter.user.repository.UserRepository;
import com.helpCenter.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder bCryptPasswordEncoder;
	@Autowired
	ResponseUsersNameDto responseUsersNameDto;
	@Autowired
	ResponseUserDto responseUserDto;

// Create Admin
	@Override
	public void createAdmin(RequestUserDTO requestUserDTO) {
		// Getting User from authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String created_by = authentication.getName();
		User user = new User(requestUserDTO);
		String userName = user.getUserName();
		User byuserName = userRepository.findByuserName(userName);
		if (byuserName != null) {
			throw new UserAlreadyExist(userName);
		}
<<<<<<< HEAD
		Role adminRole = roleRepository.getAdmin(101);
		List<Role> role = new ArrayList<>();
		role.add(adminRole);
=======
		Role roleById = roleRepository.findById(Constants.ROLE_ADMIN).get();
		List<Role> role = new ArrayList<>();
		role.add(roleById);
>>>>>>> 23e97f8e8811d3310af51014828f23724d6426f0
		user.setRole(role);
		user.setCreatedBy(created_by);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
<<<<<<< HEAD
	
=======

>>>>>>> 23e97f8e8811d3310af51014828f23724d6426f0
// CREATE NORMAL USER
	@Override
	public void createUser(RequestUserDTO userDto) {

		// Getting User from authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String created_by = authentication.getName();

		User user = new User(userDto);
		String userName = user.getUserName();
		User byuserName = userRepository.findByuserName(userName);
		if (byuserName != null) {
			throw new UserAlreadyExist(userName);
		}
		Role nonAdmin = roleRepository.getNonAdmin(102);
		List<Role> role = new ArrayList<>();
		role.add(nonAdmin);
		user.setRole(role);
		user.setCreatedBy(created_by);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

// UPDATE USER FIELDS
	@Override
	public void updateUser(String userName, UpdateUserDto updateUserDto) {

		User user = new User(updateUserDto);
		User byuserName = userRepository.findByuserName(userName);
		if (byuserName == null || byuserName.isActive() == true) {
			throw new UserNotFound(userName);
		}
		String UserName = user.getUserName();
		if (UserName != null) {
			byuserName.setUserName(UserName);
		}
		String password = user.getPassword();
		if (password != null) {
			byuserName.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		}
		userRepository.save(byuserName);
	}

// GET USER BY NAME 
	@Override
	public ResponseUserDto userByName(String userName) {

		User user = userRepository.findByuserName(userName);
		if (user == null) {
			throw new UserNotFound(userName);
		}
		return new ResponseUserDto(user);
	}

// GET LIST OF ALL USERS
	@Override
	public List<ResponseUserDto> allUser() {
		List<User> users = userRepository.findAll();
		List<ResponseUserDto> allUsers = users.stream().map(user -> responseUserDto.userToDto(user))
				.collect(Collectors.toList());
		return allUsers;
	}

// DELETE USER BY NAME 
	@Override
	public void deleteUser(String userName) {
		User user = userRepository.findByuserName(userName);
		if (user == null) {
			throw new UserNotFound(userName);
		}
		user.setActive(false);
		userRepository.save(user);
	}

// RESPONSE- ONLY USERS NAME
	@Override
	public List<ResponseUsersNameDto> usersName() {
		List<User> users = userRepository.findAll();
		List<ResponseUsersNameDto> usersName = users.stream().map(user -> responseUsersNameDto.usersName(user))
				.collect(Collectors.toList());
		return usersName;
	}

}
