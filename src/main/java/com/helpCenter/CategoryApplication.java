package com.helpCenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.helpCenter.notificationsEmails.scheduler.SchedulerForCategoryEtaExpiration;
import com.helpCenter.user.repository.RoleRepository;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class CategoryApplication{

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(CategoryApplication.class, args);
		SchedulerForCategoryEtaExpiration categoryEtaExpiration=new SchedulerForCategoryEtaExpiration();
		categoryEtaExpiration.setDaemon(true);
		categoryEtaExpiration.start();
	}
//	@Override
//	public void run(String... args) throws Exception {
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
