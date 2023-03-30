package com.helpCenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling

<<<<<<< HEAD
public class CategoryApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CategoryApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CategoryApplication.class);
=======
	public static void main(String[] args) {
		SpringApplication.run(CategoryApplication.class, args);
	}

>>>>>>> 8c7a6c42ee09bee915404cdeaa38678199017722

}
}
