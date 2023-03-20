package com.helpCenter.Incident.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.helpCenter.Incident.dtos.RequestIncidentDto;
import com.helpCenter.Incident.dtos.ResponseIncidentDto;
import com.helpCenter.Incident.dtos.UpdateIncidentDto;
import com.helpCenter.Incident.entity.ImageCreation;
import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.exceptionHandler.CategoryNotFoundException;
import com.helpCenter.Incident.exceptionHandler.IncidentNotFoundException;
import com.helpCenter.Incident.reposatiory.IncidentReposatiory;
import com.helpCenter.Incident.service.IncidentService;
import com.helpCenter.category.entity.Category;
import com.helpCenter.category.repository.CategoryRepo;
import com.helpCenter.notificationsEmails.serviceImpl.InformationProviderForEmailServiceImpl;
import com.helpCenter.user.entity.User;
import com.helpCenter.user.repository.UserRepository;

@Service
public class IncidentServiceImpl implements IncidentService {

	@Autowired
	IncidentReposatiory incidentReposatiory;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	Incident incidentClass;
	@Autowired
	InformationProviderForEmailServiceImpl providerForEmailServiceImpl;
	@Autowired
	ResponseIncidentDto responseIncidentDto;

// CREATE INCIDENT
	@Override
	public void createIncident(RequestIncidentDto incidentdto, List<MultipartFile> file) throws IOException {

		// DTO CONVERSION
		Incident incident = incidentClass.DtoToIncident(incidentdto);
		// Getting User from authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String createrName = authentication.getName();
		User name = userRepository.findByuserName(createrName);
		String code = incident.getCategoryCode();
		// Fetching Category
		Category category = categoryRepo.findByCode(code.toUpperCase());
		if (category == null) {
			throw new CategoryNotFoundException(code);
		} else {
			// Adding Images In List
			if (file != null) {
				List<ImageCreation> imageslist = new ArrayList<>();
				for (MultipartFile multipart : file) {
					ImageCreation image = new ImageCreation();
					image.setImage(multipart.getBytes());
					image.setIncident(incident);
					imageslist.add(image);
				}
				incident.setImages(imageslist);
			}
			incident.setUser(name);
			incident.setCategory(category);
			Incident savedincident = incidentReposatiory.save(incident);
			if(savedincident!=null)
			{
				providerForEmailServiceImpl.getIncidentCategoryDetails(savedincident);
			}
		}
	}

// GET ALL INCIDENTS
	@Override
	public List<ResponseIncidentDto> getAllIncidents() {
		List<Incident> incidents = incidentReposatiory.findAll();
		List<ResponseIncidentDto> incidentDto = incidents.stream()
				.map(incident -> responseIncidentDto.incidentToIncident_Dto(incident)).collect(Collectors.toList());
		return incidentDto;
	}

// GET INCIDENT BY ID
	@Override
	public ResponseIncidentDto getIncidentById(int id) {
		Incident incident = incidentReposatiory.findById(id);
		System.out.println(incident);
		if (incident == null) {
			throw new IncidentNotFoundException(id);
		}
		ResponseIncidentDto incidentdto = responseIncidentDto.incidentToIncident_Dto(incident);
		return incidentdto;
	}

// UPDATE INCIDENT
	@Override
	public void updateIncident(int id, UpdateIncidentDto incidentdto, List<MultipartFile> file) throws IOException {
		// Fetching Incident To be Updated
		Incident updateIncident = incidentReposatiory.findById(id);

		if (incidentdto != null) {
			// DTO CONVERSION
			Incident incident = incidentClass.UpdateDtoToIncident(incidentdto);
			// Fetching Category
			String categoryCode = incident.getCategoryCode();
			if (categoryCode != null) {
				Category category = categoryRepo.findByCode(categoryCode.toUpperCase());
				if (category == null) {
					throw new CategoryNotFoundException(categoryCode);
				}
				updateIncident.setCategory(category);
				
			}
			if(incident.getLastmailSendedTime()!=null)
			{
				updateIncident.setLastmailSendedTime(incident.getLastmailSendedTime());
			}
			if (incident.getTitle() != null) {
				updateIncident.setTitle(incident.getTitle());
			}
			if (incident.getCategory() != null) {
				updateIncident.setCategoryCode(incident.getCategoryCode());
			}
			if (incident.getDescription() != null) {
				updateIncident.setDescription(incident.getDescription());
			}
			if (incident.getPriority() != null) {
				updateIncident.setPriority(incident.getPriority());
			}
		}
		if (file != null) {
			List<ImageCreation> imageslist = new ArrayList<>();
			for (MultipartFile multipart : file) {
				ImageCreation image = new ImageCreation();
				image.setImage(multipart.getBytes());
				image.setIncident(updateIncident);
				imageslist.add(image);
			}
			updateIncident.setImages(imageslist);
		}
		incidentReposatiory.save(updateIncident);
	}
}
