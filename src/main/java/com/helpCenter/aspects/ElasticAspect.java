package com.helpCenter.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.restTemplate.HttpHeader;

@Aspect
@Component
public class ElasticAspect {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	HttpHeader httpHeader;

	// STORING DATA INTO ELASTIC SEARCH
	@AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.createIncident(..))", returning = "incident")
	public void storeIncidentInElasticsearchAsync(Incident incident) {
		try {
			HttpHeaders headers = httpHeader.createHeadersWithAuthentication();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Incident> requestEntity = new HttpEntity<>(incident,headers);
			String docId = Integer.toString(incident.getId());
			String url = String.format("https://localhost:9200/incident/_doc/%s",docId);
			restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Updating data into Elastic Search
		@AfterReturning(pointcut = "execution(* com.helpCenter.Incident.serviceImpl.IncidentServiceImpl.updateIncident(..))", returning = "incident")
		public void updatingIncidentInElasticsearchAsync(Incident incident) {
			try {
				HttpHeaders headers = httpHeader.createHeadersWithAuthentication();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<Incident> requestEntity = new HttpEntity<>(incident,headers);
				int id = incident.getId();
				String url = String.format("https://localhost:9200/incident/_doc/%s",id);
				restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
}
