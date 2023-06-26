package com.helpCenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;


@EnableJpaAuditing
//@EnableScheduling
@SpringBootApplication
public class CategoryApplication {

//	@Bean
//	public RestTemplate restTemplate()
//	{
//		return new RestTemplate();
//	}
	
	public static void main(String[] args){
		SpringApplication.run(CategoryApplication.class, args);
		
		
	}

}
