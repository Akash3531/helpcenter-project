package com.helpCenter.Incident.incidenthandler.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.incidenthandler.Entity.IncidentHandler;
import com.helpCenter.Incident.incidenthandler.reposatiory.IncidentHandlerReposatiory;
import com.helpCenter.Incident.incidenthandler.service.IncidentHandlerService;
import com.helpCenter.requestHandlers.entity.HandlerDetails;
import com.helpCenter.user.entity.User;

@Service
public class IncidentHandlerServiceImpl implements IncidentHandlerService {

	@Autowired
	IncidentHandlerReposatiory handlerReposatiory;

	@Override
	public void saveIncidentHandler(Incident incident) {

		IncidentHandler incidentHandler = new IncidentHandler();
		List<HandlerDetails> handlers = incident.getCategory().getRequestHandler().getHandler();
		Map<Integer, List<String>> resource = new HashMap<>();
		for (HandlerDetails detail : handlers) {
			resource.put(detail.getLevel(), detail.getResources());
		}
		List<String> nameOfHandlers = resource.get(handlers.size());
		incidentHandler.setName(nameOfHandlers);
		incidentHandler.setIncident(incident);
		incidentHandler.setStatus(incident.getStatus());
		handlerReposatiory.save(incidentHandler);

	}

	@Override
	public void getIncidentHandler(Incident incident) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		IncidentHandler incidentHandler = handlerReposatiory.getHandler(incident.getId());
		List<String> handlers = new ArrayList<>();
		handlers.add(userName);
		incidentHandler.setName(handlers);
		incidentHandler.setStatus(incident.getStatus());
		handlerReposatiory.save(incidentHandler);

	}

	@Override
	public IncidentHandler getHandlerByStatus() {

		return null;
	}

	@Override
	public void updateHandlerOnEtaExpiration(List<Incident> incidents) {
		for (Incident incident : incidents) {
			IncidentHandler incidentHandler = handlerReposatiory.getHandler(incident.getId());
			int eta = incident.getCategory().getEtaInMinutes();
			long createdTime = incident.getCreatedDate().getTime();
			long currentTime = new Date().getTime();
			long diffBetweenCreatedTimeAndSchedulerRunTime = currentTime - createdTime;// Difference between created
																						// time and current time
			long diffrenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffBetweenCreatedTimeAndSchedulerRunTime);

			int handlerLevel = (int) (diffrenceInMinutes / eta);
			Map<Integer, List<String>> handlerDetails = new LinkedHashMap<>();
			List<HandlerDetails> handlers = incident.getCategory().getRequestHandler().getHandler();
			for (HandlerDetails details : handlers) {
				handlerDetails.put(details.getLevel(), details.getResources());// put handler details in key
			}
			List<String> handlerNames = new ArrayList<>();
			if (handlerLevel < handlers.size()) {
				List<String> handlersName = handlerDetails.get(handlers.size() - handlerLevel);// getting handler of
																								// incident
				for (String handlerName : handlersName) {

					handlerNames.add(handlerName);

				}
				incidentHandler.setName(handlerNames);
				handlerReposatiory.save(incidentHandler);
			}
		}
	}
}
