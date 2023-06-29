package com.helpCenter.Incident.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.helpCenter.Incident.dtos.GetIncidentbyCategory;
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
import com.helpCenter.restTemplate.HttpHeader;
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
	ResponseIncidentDto responseIncidentDto;
	@Autowired
	GetIncidentbyCategory getIncidentbyCategory;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	HttpHeader httpHeader;

// CREATE INCIDENT
	@Override
	public Incident createIncident(RequestIncidentDto incidentdto, List<MultipartFile> file) throws IOException {
 
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
        	incident.setEtaInMinutes(category.getEtaInMinutes());
			incident.setEtaInValidation(category.getEtaInValidation());


			// Data Storing into Elastic Search
			HttpHeaders headers = httpHeader.createHeadersWithAuthentication();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Incident> requestEntity = new HttpEntity<>(incident, headers);
			restTemplate.exchange("https://localhost:9200/incident/_doc", HttpMethod.POST, requestEntity, String.class);
			
			// Data Storing into Database

			Incident savedincident = incidentReposatiory.save(incident);
			return savedincident;
			
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
		if (incident == null) {
			throw new IncidentNotFoundException(id);
		}
		ResponseIncidentDto incidentdto = responseIncidentDto.incidentToIncident_Dto(incident);
		return incidentdto;
	}

// UPDATE INCIDENT
	@Override
	public Incident updateIncident(int id, UpdateIncidentDto incidentdto, List<MultipartFile> files)
			throws IOException {
		// Fetching Incident To be Updated
		Incident updateIncident = incidentReposatiory.findById(id);
		if (updateIncident == null) {
			throw new IncidentNotFoundException(id);
		} else {
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
				if (incident.getStatus() != null) {
					updateIncident.setStatus(incident.getStatus());
				}
				if (incident.getLastmailSendedTime() != null) {
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
			if (files != null) {
				List<ImageCreation> images = new ArrayList<>();
				for (MultipartFile file : files) {
					ImageCreation image = new ImageCreation();
					image.setImage(file.getBytes());
					image.setIncident(updateIncident);
					images.add(image);
				}
				updateIncident.setImages(images);
			}
			Incident saveIncident = incidentReposatiory.save(updateIncident);
			return saveIncident;
		}
	}

//Get INCIDENT BY USER
	@Override
	public List<GetIncidentbyCategory> getIncidentbyUser(int user_id, Integer pageNumber, Integer pageSize) {

		Pageable p = PageRequest.of(pageNumber, pageSize);
		List<Incident> incidents = incidentReposatiory.findIncidentByUserId(user_id, p);
		if (incidents == null) {
			throw new IncidentNotFoundException(user_id);
		}
		List<GetIncidentbyCategory> incidentList = incidents.stream()
				.map((incident) -> getIncidentbyCategory.UserIncident(incident)).collect(Collectors.toList());
		return incidentList;
	}

//Get INCIDENT BY CODE
	@Override
	public List<GetIncidentbyCategory> getIncidentbyCategoryCode(String code, Integer pageNumber, Integer pageSize) {
		Pageable p = PageRequest.of(pageNumber, pageSize);
		List<Incident> incidents = incidentReposatiory.findIncidentByCode(code, p);
		if (incidents == null) {
			throw new IncidentNotFoundException(code);
		}
		List<GetIncidentbyCategory> incidentDto = incidents.stream()
				.map((incident) -> getIncidentbyCategory.UserIncident(incident)).collect(Collectors.toList());
		return incidentDto;
	}

// Get Incidents from ELastic Search
	@Override
	public List<ResponseIncidentDto> getAllIncidentsFromElastic(String index) {
		String url = "https://localhost:9200/" + index + "/_search";

		HttpHeaders headers = httpHeader.createHeadersWithAuthentication();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<Map<String, Object>>() {
				});
		List<ResponseIncidentDto> incidents = null;
		if (response.getStatusCode().is2xxSuccessful()) {
			Map<String, Object> responseBody = response.getBody();
			if (responseBody != null) {
				Map<String, Object> hits = (Map<String, Object>) responseBody.get("hits");
				if (hits != null) {
					List<Map<String, Object>> hitsList = (List<Map<String, Object>>) hits.get("hits");
					incidents = hitsList.stream().map(hit -> responseIncidentDto.convertToIncident(hit)).collect(Collectors.toList());
				}
			}
		}
		return incidents;
	}


}
