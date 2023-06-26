//package com.helpCenter.aspects;
//
//import java.util.Date;
//import java.util.concurrent.ExecutionException;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import com.helpCenter.Incident.entity.Incident;
//import com.helpCenter.comment.entity.Comment;
//import com.helpCenter.kafkaSetUp.model.Message;
//
//@EnableKafka
//@Aspect
//@Component
//public class RealTimeNotificationAspect {
//	@Autowired
//	private KafkaTemplate<String, Message> kafkaTemplate;
//	@Autowired
//	@Qualifier(value = "test")
//	NewTopic newTopic;
//
//	// Send notification on incident creation
//	@AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.createIncident(..))", returning = "Incident")
//	public void sentKafkaNotification_afterCreatingIncident(Incident Incident) {
//		Message message = new Message();
//		message.setTimestamp(new Date().toString());
//		message.setSender(Incident.getUser().getUsername());
//		message.setContent(Incident.getDescription());
//		try {
//			// Sending the message to kafka topic queue
//			kafkaTemplate.send(newTopic.name(), message).get();
//		} catch (InterruptedException | ExecutionException e) {
//			throw new RuntimeException(e);
//		}
//
//	}
//
//	// Send notification on comment creation
//	@AfterReturning(pointcut = "execution(* com.helpCenter.comment.serviceImpl.CommentServiceimpl.createComment(..))", returning = "comment")
//	public void sentKafkaNotification_afterComment(Comment comment) {
//		Message message = new Message();
//		message.setTimestamp(new Date().toString());
//		message.setSender(comment.getUser().getUsername());
//		message.setContent(comment.getComments());
//		try {
//			// Sending the message to kafka topic queue
//			kafkaTemplate.send(newTopic.name(), message).get();
//		} catch (InterruptedException | ExecutionException e) {
//			throw new RuntimeException(e);
//		}
//	}
//}
