//package com.helpCenter.kafkaSetUp.producerAndConsumerConfig;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import com.helpCenter.kafkaSetUp.model.Message;
//
//@EnableKafka
//@Configuration
//public class KafkaProducerConfig {
//
//	@Value(value = "${spring.kafka.bootstrap-servers}")
//	private String bootstrapAddress;
//	
//	@Bean
//	public ProducerFactory<String, Message> producerFactory() {
//		return new DefaultKafkaProducerFactory<>(producerConfigurations());
//	}
//
//	@Bean
//	public Map<String, Object> producerConfigurations() {
//		Map<String, Object> configurations = new HashMap<>();
//		configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
//		configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//		configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//		return configurations;
//	}
//
//	@Bean
//	public ProducerFactory<String, String> producerFactoryForQueue() {
//		return new DefaultKafkaProducerFactory<>(producerConfigurations());
//	}
//	
//	@Bean
//	public Map<String, String> producerConfigurationsForQueue() {
//		Map<String, String> configurations = new HashMap<>();
//		configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
////		configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
////		configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//		return configurations;
//	}
//	@Bean
//	public KafkaTemplate<String, Message> kafkaTemplateForCreation() {
//		return new KafkaTemplate<>(producerFactory());
//	}
//	
//	
//	@Bean(name="templateForQueue")
//	public KafkaTemplate<String,String> kafkaTemplateForQueue()
//	{
//		return new KafkaTemplate<>(producerFactoryForQueue());
//	}
//}