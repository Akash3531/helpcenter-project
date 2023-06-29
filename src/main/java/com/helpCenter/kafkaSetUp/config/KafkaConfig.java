
package com.helpCenter.kafkaSetUp.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaConfig {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean("test")
	public NewTopic userTestTopic() {
		return TopicBuilder.name("test-user").partitions(1).replicas(1).build();
	}

	@Bean("queue")
	public NewTopic topicForQueue() {
		return TopicBuilder.name("topicForQueue").partitions(1).replicas(1).build();
	}

	

//package com.helpCenter.kafkaSetUp.config;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//import org.springframework.kafka.core.KafkaAdmin;
//
//@Configuration
//public class KafkaConfig {
//
//	@Value(value = "${spring.kafka.bootstrap-servers}")
//	private String bootstrapAddress;
//
//	@Bean
//	public KafkaAdmin kafkaAdmin() {
//		Map<String, Object> configs = new HashMap<>();
//		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//		return new KafkaAdmin(configs);
//	}
//
//	@Bean("test")
//	public NewTopic userTestTopic() {
//		return TopicBuilder.name("test-user").partitions(2).replicas(2).build();
//	}
//
//	@Bean("queue")
//	public NewTopic topicForQueue() {
//		return TopicBuilder.name("topicForQueue").partitions(1).replicas(1).build();
//	}
//}


}

