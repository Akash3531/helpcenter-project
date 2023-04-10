package com.helpCenter.notificationsEmails.informationProviderServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpCenter.Incident.dtos.UpdateIncidentDto;
import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.serviceImpl.IncidentServiceImpl;
import com.helpCenter.category.entity.Category;
import com.helpCenter.comment.entity.Comment;
import com.helpCenter.notificationsEmails.informationProviderService.InformationProviderForEmailService;
import com.helpCenter.notificationsEmails.mailSenderServiceImpl.MailSenderServiceImpl;
import com.helpCenter.requestHandlers.entity.HandlerDetails;
import com.helpCenter.requestHandlers.entity.RequestHandler;
import com.helpCenter.user.repository.UserRepository;

@Service
public class InformationProviderForEmailServiceImpl implements InformationProviderForEmailService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	MailSenderServiceImpl mailSenderServiceImpl;
	@Autowired
	IncidentServiceImpl incidentServiceImpl;

	@Override
	public void getIncidentCategoryDetails(Incident incident) {

		int eta = incident.getCategory().getEtaInMinutes();
		long createdTime = incident.getCreatedDate().getTime();
		long shedulerRunTime = new Date().getTime();
		long diffBetweenCreatedTimeAndSchedulerRunTime = shedulerRunTime - createdTime;// Difference between created
																						// time and current time
		long diffrenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffBetweenCreatedTimeAndSchedulerRunTime);

		int handlerLevel = (int) (diffrenceInMinutes / eta);
		String[] toEmails = null;
		Map<Integer, List<String>> handlerDetails = new LinkedHashMap<>();
		List<HandlerDetails> handlers = incident.getCategory().getRequestHandler().getHandler();
		for (HandlerDetails details : handlers) {
			handlerDetails.put(details.getLevel(), details.getResources());// put handler details in key
																			// value pair
		}
		if (handlerLevel < handlers.size()) {
			List<String> handlersName = handlerDetails.get(handlers.size() - handlerLevel);
			for (String handlerName : handlersName) {
				toEmails = userRepository.findUserEmail(handlerName);
			}
			mailSenderServiceImpl.sendEmailForIncident(toEmails, incident.getTitle(), incident.getDescription());
			UpdateIncidentDto incidentDto = new UpdateIncidentDto();
			incidentDto.setLastmailSendedTime(new Date());
			try {
				incidentServiceImpl.updateIncident(incident.getId(), incidentDto, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void getCommentDetails(Comment comment) {
		Map<Integer, List<String>> handlersWithLevel = new LinkedHashMap<>();
		String[] toEmails = null;
		RequestHandler requestHandler = comment.getIncident().getCategory().getRequestHandler();
		List<HandlerDetails> handlers = requestHandler.getHandler();
		// putting handler details into map
		for (HandlerDetails handlerDetails : handlers) {
			handlersWithLevel.put(handlerDetails.getLevel(), handlerDetails.getResources());
		}
		// fetching email of related handler
		List<String> list = handlersWithLevel.get(handlers.size());
		for (String name : list) {
			toEmails = userRepository.findUserEmail(name);
		}
		mailSenderServiceImpl.sendEmailAfterCommentCreation(toEmails, comment.getComments(),
				comment.getIncident().getTitle());
	}

	@Override
	public void getDetailOfStatusUpdate(Incident updateIncident) {
		String email = updateIncident.getUser().getEmail();
		String title = updateIncident.getTitle();
		String status = updateIncident.getStatus();
		mailSenderServiceImpl.sendMailOnStatusUpdate(email, title, status);
	}

	@Override
	public void getCategoryCreateDetails(Category category) {
		String createdBy = category.getCreatedBy();
		String name = category.getName();
		mailSenderServiceImpl.sendMailOnCategoryCreation(createdBy, name);
	}

	@Override
	public void getDetailsOnCategoryUpdation(Category category) {
		String[] userEmails=null;
		String name = category.getName();
		String code = category.getCode();
		int etaInMinutes = category.getEtaInMinutes();
		RequestHandler requestHandler = category.getRequestHandler();
		String createdBy = category.getCreatedBy();
		String updatedBy = category.getUpdatedBy();
		List<String> users = new ArrayList<>();
		users.add(createdBy);
		users.add(updatedBy);
		for (String user : users) {
			userEmails = userRepository.findUserEmail(user);
		}
		mailSenderServiceImpl.sendMailOnCategoryUpdation(userEmails, name,code, etaInMinutes, requestHandler);
	}

}
