package com.helpCenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.helpCenter.user.entity.Constants;
import com.helpCenter.user.entity.Role;
import com.helpCenter.user.repository.RoleRepository;

@SpringBootApplication
@EnableJpaAuditing
public class CategoryApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(CategoryApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Role role1 = new Role();
		role1.setRoleId(Constants.ROLE_ADMIN);
		role1.setRole("ROLE_ADMIN");

		Role role2 = new Role();
		role2.setRoleId(Constants.ROLE_NORMAL);
		role2.setRole("ROLE_NORMAL");

		List<Role> roles = List.of(role1, role2);
		roleRepository.saveAll(roles);

	}

}
