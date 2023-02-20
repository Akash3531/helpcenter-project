package com.helpCenter.security.configration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.helpCenter.user.entity.User;
import com.helpCenter.user.exceptionHandler.UserNotFound;
import com.helpCenter.user.repository.UserRepository;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByuserName(username);
		if (user == null) {
			throw new UserNotFound(username);
		}

		return user;
	}

}
