//package com.helpCenter.aspects;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import com.helpCenter.Incident.entity.Incident;
//import com.helpCenter.Incident.reposatiory.IncidentReposatiory;
//
//@Component
//@Aspect
//public class QueueNumberAspect {
//
//	@Autowired
//	@Qualifier("templateForQueue")
//	private KafkaTemplate<String, String> kafkaTemplate;
//
//	@Autowired
//	@Qualifier("queue")
//	private NewTopic queue;
//
//	@Autowired
//	IncidentReposatiory incidentReposatiory;
//
//	// Send queue number of a incident
//	@AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.createIncident(..))", returning = "Incident")
//	public void get_QueueNumberOn_IncidetnCreation(Incident Incident) {
//		List<Integer> idsOfIncident = new ArrayList<>();
//		String categoryCode = Incident.getCategory().getCode();
//		int id = Incident.getId();
//		List<Incident> byCategoryCodeAndStatus = incidentReposatiory.findIncidentByCategoryCodeAndStatus(categoryCode);
//		for (Incident incidentFromList : byCategoryCodeAndStatus) {
//			int idOfIncident = incidentFromList.getId();
//			idsOfIncident.add(idOfIncident);
//		}
//		int indexOf = idsOfIncident.indexOf(id);
//		String message = new String();
//		message = "Your ticket number is" + " " + Incident.getId() + " " + "your incident is" + " " + ++indexOf
//				+ " in the queue.Your incident will be resolve shortly";
//		try {
//			// Sending the message to kafka topic queue
//			kafkaTemplate.send(queue.name(), message).get();
//		} catch (InterruptedException | ExecutionException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	// send queue number of a incident if any incident get update
//	@AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.updateIncident(..))", returning = "incident")
//	public void get_queueNumberOn_StatusUpdate(Incident incident) {
//		if (incident.getStatus() != null && !incident.getStatus().equals("ToDo")) {
//			List<Integer> listOfIds = new ArrayList<>();
//			List<Incident> byCategoryCodeAndStatus = incidentReposatiory
//					.findIncidentByCategoryCodeAndStatus(incident.getCategoryCode());
//			for (Incident incidentFromList : byCategoryCodeAndStatus) {
//				listOfIds.add(incidentFromList.getId());
//			}
//			for (Integer idOfIncident : listOfIds) {
//				int indexOf = listOfIds.indexOf(idOfIncident);
//				String message = new String();
//				message = "Your ticket number is" + " " + idOfIncident + " " + "your incident is" + " " + ++indexOf
//						+ " in the queue.Your incident will be resolve shortly";
//				try {
//					// Sending the message to kafka topic queue
//					kafkaTemplate.send(queue.name(), message).get();
//				} catch (InterruptedException | ExecutionException e) {
//					throw new RuntimeException(e);
//				}
//			}
//		}
//	}
//}
