package com.helpCenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.helpCenter.notificationsEmails.scheduler.SchedulerForCategoryEtaExpiration;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class CategoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoryApplication.class, args);
	}

}
