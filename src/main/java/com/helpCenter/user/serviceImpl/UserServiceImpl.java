package com.helpCenter.user.serviceImpl;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.reposatiory.IncidentReposatiory;
import com.helpCenter.notificationsEmails.mailSenderServiceImpl.MailSenderServiceImpl;
import com.helpCenter.user.dto.RequestUserDTO;
import com.helpCenter.user.dto.ResponseUserDto;
import com.helpCenter.user.dto.ResponseUsersNameDto;
import com.helpCenter.user.dto.UpdateUserDto;
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
	@Autowired
	IncidentReposatiory incidentReposatiory;
	@Autowired
	MailSenderServiceImpl senderServiceImpl;

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
		System.out.println(userDto.getRole().getRole());
		Role userRole = roleRepository.getRole(userDto.getRole().getRole().toUpperCase());
		List<Role> role = new ArrayList<>();
		role.add(userRole);
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
		String department = user.getDepartment();
		if (department != null) {
			byuserName.setDepartment(department);
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
		user.setActive(true);
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

// Assign tickets
	@Override
	public void assignTicket(int Ticketid, String User) {

		// Fetching Inicident
		Incident incident = incidentReposatiory.findById(Ticketid);
		// Fetching User
		User user = userRepository.findByuserName(User);
		String email = user.getEmail();
		// Sending mail
		senderServiceImpl.sendEmailForIncidentAssign(email, incident.getTitle(), incident.getDescription());

	}

}
