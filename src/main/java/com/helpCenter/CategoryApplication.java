package com.helpCenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class CategoryApplication  {

//	@Autowired
//	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(CategoryApplication.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void setUp() throws Exception {
//		
//		Role role1 = new Role();
//		role1.setRoleId(Constants.ROLE_ADMIN);
//		role1.setRole("ROLE_ADMIN");
//
//		Role role2 = new Role();
//		role2.setRoleId(Constants.ROLE_NORMAL);
//		role2.setRole("ROLE_NORMAL");
//
//		List<Role> roles = List.of(role1, role2);
//		roleRepository.saveAll(roles);
//
//	}

}
