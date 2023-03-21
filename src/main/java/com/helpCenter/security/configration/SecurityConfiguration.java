package com.helpCenter.security.configration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired
	private CustomeUserDetailsService customeUserDetailService;

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() throws Exception {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public SecurityFilterChain chain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/user/{name}").hasAnyRole("ADMIN", "NORMAL")
		.requestMatchers(HttpMethod.PATCH, "/user/{userName}").hasAnyRole("ADMIN", "NORMAL")
		.requestMatchers(HttpMethod.POST, "/incident/").hasAnyRole("ADMIN", "NORMAL")
		.requestMatchers("/user/**", "/category/**", "/iamge/**", "/incident/**", "/comment/**").permitAll().anyRequest()
				.authenticated().and().httpBasic();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authenticationProvider(authenticationProvider());
		DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
		return defaultSecurityFilterChain;
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() throws Exception {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(customeUserDetailService);
		authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return authenticationProvider;
	}

}
