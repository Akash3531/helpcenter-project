package com.helpCenter.kafkaSetUp.controller;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpCenter.kafkaSetUp.model.Message;

@RestController
@RequestMapping("/kafka")
public class ChatController {

	@Autowired
	private KafkaTemplate<String, Message> kafkaTemplate;

	@Autowired
	@Qualifier("test")
	NewTopic topic;

	@PostMapping("/")
	public void sendMessage(@RequestBody Message message) {
		message.setTimestamp(LocalDateTime.now().toString());
		try {
			// Sending the message to kafka topic queue
			kafkaTemplate.send(topic.name(), message).get();

		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

}
