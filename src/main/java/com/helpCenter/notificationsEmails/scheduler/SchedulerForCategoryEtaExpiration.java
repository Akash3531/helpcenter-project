package com.helpCenter.notificationsEmails.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.reposatiory.IncidentReposatiory;
import com.helpCenter.notificationsEmails.informationProviderServiceImpl.InformationProviderForEmailServiceImpl;
import com.helpCenter.notificationsEmails.mailSenderServiceImpl.MailSenderServiceImpl;
import com.helpCenter.user.repository.UserRepository;

@Component
public class SchedulerForCategoryEtaExpiration {

	@Autowired
	UserRepository userRepository;
	@Autowired
	IncidentReposatiory incidentReposatiory;
	@Autowired
	MailSenderServiceImpl mailSenderServiceImpl;
	@Autowired
	InformationProviderForEmailServiceImpl providerForEmailServiceImpl;


	@Scheduled(cron = "0 0/30 * * * *") // this code will be executed every 30
	//@Scheduled(fixedDelay = 3000000)

	public void runForCategoryEta() {
		List<Incident> incidents = incidentReposatiory.findAll();// get all saved incident
		for (Incident incident : incidents) {
			String status = incident.getStatus();// getting status of incident
			if (status != "done" && status!="hold") {
				int eta = incident.getCategory().getEtaInMinutes();
				long etaInMillis = eta * 60 * 1000;
				long lastmailSendedTime = incident.getLastmailSendedTime().getTime();
				if (lastmailSendedTime + etaInMillis < System.currentTimeMillis()) {
					providerForEmailServiceImpl.getIncidentCategoryDetails(incident);
				}

			}

		}
	}
}
