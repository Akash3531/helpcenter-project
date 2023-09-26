package com.helpCenter.Incident.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
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
import com.helpCenter.elasticSearch.aggregators.AggregationResponse;
import com.helpCenter.elasticSearch.aggregators.IntervalDataResponseAggregation;
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
	public List<ResponseIncidentDto> getAllIncidentsFromElastic(String index, String field) {

		String url = "https://localhost:9200/" + index + "/_search?q=" + field;
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
					incidents = hitsList.stream().map(hit -> responseIncidentDto.convertToIncident(hit))
							.collect(Collectors.toList());
				}
			}
		}
		return incidents;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AggregationResponse> getBucketListFromElastic(String index, String field) {
		String url = "https://localhost:9200/" + index + "/_search?q=*";

		HttpHeaders headers = httpHeader.createHeadersWithAuthentication();
		headers.setContentType(MediaType.APPLICATION_JSON);

		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("size", 0);

		JsonObject termsAgg = new JsonObject();
		termsAgg.addProperty("field", field + ".keyword");

		JsonObject categoryAgg = new JsonObject();
		categoryAgg.add("terms", termsAgg);

		JsonObject aggs = new JsonObject();
		aggs.add("category", categoryAgg);

		requestBody.add("aggs", aggs);

		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Map<String, Object>>() {
				});

		List<AggregationResponse> incidents = new ArrayList<>();
		if (response.getStatusCode().is2xxSuccessful()) {
			Map<String, Object> responseBody = response.getBody();
			if (responseBody != null) {
				Map<String, Object> aggregations = (Map<String, Object>) responseBody.get("aggregations");
				if (aggregations != null) {
					Map<String, Object> categoryAggResult = (Map<String, Object>) aggregations.get("category");
					if (categoryAggResult != null) {
						List<Map<String, Object>> buckets = (List<Map<String, Object>>) categoryAggResult
								.get("buckets");
						for (Map<String, Object> bucket : buckets) {
							String term = (String) bucket.get("key");
							Number docCountNumber = (Number) bucket.get("doc_count"); // Cast to Number
							long docCount = docCountNumber.longValue();

							AggregationResponse resp = new AggregationResponse();
							resp.setTerm(term);
							resp.setDoc_Count(docCount);
							incidents.add(resp);

						}
					}
				}
			}
		}
		
		return incidents;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IntervalDataResponseAggregation> getIntervalDataFromElastic(String index, String interval) {

		String url = "https://localhost:9200/" + index + "/_search?q=*";

		HttpHeaders headers = httpHeader.createHeadersWithAuthentication();
		headers.setContentType(MediaType.APPLICATION_JSON);

		JsonObject dateHistogramAgg = new JsonObject();
		dateHistogramAgg.addProperty("field", "createdDate");
		dateHistogramAgg.addProperty("calendar_interval", interval);

		JsonObject incidentCreateAgg = new JsonObject();
		incidentCreateAgg.add("date_histogram", dateHistogramAgg);

		JsonObject aggs = new JsonObject();
		aggs.add("incident_create", incidentCreateAgg);

		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("size", 0);
		requestBody.add("aggs", aggs);

		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Map<String, Object>>() {
				});

		List<IntervalDataResponseAggregation> incidents = new ArrayList<>();
		if (response.getStatusCode().is2xxSuccessful()) {
			Map<String, Object> responseBody = response.getBody();
			if (responseBody != null) {
				Map<String, Object> aggregations = (Map<String, Object>) responseBody.get("aggregations");
				if (aggregations != null) {
					Map<String, Object> categoryAggResult = (Map<String, Object>) aggregations.get("incident_create");
					if (categoryAggResult != null) {
						List<Map<String, Object>> buckets = (List<Map<String, Object>>) categoryAggResult
								.get("buckets");
						for (Map<String, Object> bucket : buckets) {
							long date = (long) bucket.get("key");
							Number docCountNumber = (Number) bucket.get("doc_count"); // Cast to Number
							long docCount = docCountNumber.longValue();

							IntervalDataResponseAggregation respinterval = new IntervalDataResponseAggregation(date,
									docCount);
							incidents.add(respinterval);

						}
					}
				}
			}
		}
		return incidents;

	}
	
	public List<ResponseIncidentDto> getDataBydateAndTimeFromElastic(String index, long startDate, long endDate) {
		String url = "https://localhost:9200/" + index + "/_search";
		HttpHeaders headers = httpHeader.createHeadersWithAuthentication();
		headers.setContentType(MediaType.APPLICATION_JSON);
		long startTimestamp = startDate;
	    long endTimestamp = endDate;
	    JsonObject dateRangeAgg = new JsonObject();
	    dateRangeAgg.addProperty("gte", startTimestamp);
	    dateRangeAgg.addProperty("lte", endTimestamp);
	    JsonObject rangeQuery = new JsonObject();
	    rangeQuery.add("createdDate", dateRangeAgg);
	    JsonObject query = new JsonObject();
	    query.add("range", rangeQuery);
	    JsonObject requestBody = new JsonObject();
	    requestBody.add("query", query);
	    RestTemplate restTemplate = new RestTemplate();
	    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);
	  ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,new ParameterizedTypeReference<Map<String, Object>>() {
		});
	  List<ResponseIncidentDto> incidents = null;
		if (response.getStatusCode().is2xxSuccessful()) {
			Map<String, Object> responseBody = response.getBody();
			if (responseBody != null) {
				Map<String, Object> hits = (Map<String, Object>) responseBody.get("hits");
				if (hits != null) {
					List<Map<String, Object>> hitsList = (List<Map<String, Object>>) hits.get("hits");
					incidents = hitsList.stream().map(hit -> responseIncidentDto.convertToIncident(hit))
							.collect(Collectors.toList());
				}
			}
		}
		return incidents;
	}
}
