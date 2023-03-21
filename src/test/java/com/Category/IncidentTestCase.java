package com.Category;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpCenter.CategoryApplication;
import com.helpCenter.Incident.dtos.RequestIncidentDto;
import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.reposatiory.IncidentReposatiory;
import com.helpCenter.category.entity.Category;
import com.helpCenter.category.repository.CategoryRepo;
import com.helpCenter.user.dto.RequestUserDTO;
import com.helpCenter.user.entity.User;
import com.helpCenter.user.repository.UserRepository;

@WebAppConfiguration
@ContextConfiguration(classes = CategoryApplication.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user", password = "password", roles = "ADMIN")
public class IncidentTestCase {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	IncidentReposatiory incidentReposatiory;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@Test
	void contextLoads() {

	}

	@BeforeEach
	void setup() {

		incidentReposatiory.deleteAll();
		categoryRepo.deleteAll();
		userRepository.deleteAll();
	}

	// create incident
	@Test
	public void givenIncidentObject_whenCreateIncident_thenReturnStatusCreated() throws Exception {
		// given - precondition

		Category category = new Category("software", "SOFTWARE@12");
		categoryRepo.save(category);

		RequestIncidentDto incidentDto = new RequestIncidentDto();
		incidentDto.setTitle("Software failure");
		incidentDto.setDescription("postman is need adminstration user name and password");
		incidentDto.setCategoryCode("SOFTWARE@12");

		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = Obj.writeValueAsString(incidentDto);
		MockMultipartFile jsonFile = new MockMultipartFile("incident", "", "application/json", jsonStr.getBytes());

		MockMultipartFile file = new MockMultipartFile("image", "C:\\Users\\sahotahitesh\\Downloads\\w.jpg",
				MediaType.MULTIPART_FORM_DATA_VALUE, "".getBytes());
		// when - action or behavior
		ResultActions response = mockMvc.perform(multipart("/incident/").file(jsonFile).file(file));
		// then -verify result
		response.andExpect(status().isCreated()).andDo(print());

	}

	// update incident
	@Test
	public void givenUpdatedIncident_whenUpdateIncident_thenReturnStatusOk() throws Exception {
		// given - precondition
		Category category = new Category("hardware", "HARDWARE@33");
		categoryRepo.save(category);

		Incident incident = new Incident();
		incident.setCategoryCode(category.getCode());
		incident.setTitle("mouse problem");
		incident.setDescription("mouse is not working");
		incidentReposatiory.save(incident);

		RequestIncidentDto incidentDto = new RequestIncidentDto();
		incidentDto.setTitle("mouse problem");
		incidentDto.setDescription("mouse wire is damage");
		incidentDto.setCategoryCode(category.getCode());

		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = Obj.writeValueAsString(incidentDto);
		MockMultipartFile jsonFile = new MockMultipartFile("incident", "", "application/json", jsonStr.getBytes());

		MockMultipartFile file = new MockMultipartFile("image", "C:\\Users\\akash\\Pictures\\bankimage.jpg",
				MediaType.MULTIPART_FORM_DATA_VALUE, "".getBytes());
		// when - action or behavior

		MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/incident/{id}",
				incident.getId());
		builder.with(new RequestPostProcessor() {
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setMethod("PATCH");
				return request;
			}
		});
		ResultActions response = mockMvc.perform(builder.file(jsonFile).file(file));
		// then - verify output
		response.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
	}

	// get incident by id
	@Test
	public void givenIncident_whenGetIncidentById_thenReturnSavedIncident() throws Exception {
		// given - precondition
		Category category = new Category("hardware", "HARDWARE@33");
		categoryRepo.save(category);

		Incident incident = new Incident();
		incident.setCategoryCode(category.getCode());
		incident.setTitle("Keyboard problem");
		incident.setDescription("Keyboard is not working");
		incidentReposatiory.save(incident);
		// when - action or behavior
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/incident/{id}", incident.getId()));
		// then - verify output
		response.andDo(print()).andExpect(jsonPath("$.title", is(incident.getTitle())))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// get all incident
	@Test
	public void gevenIncident_whenGetAllIncident_thenReturnSavedIncident() throws Exception {
		Category category = new Category("hardware", "HARDWARE@33");
		categoryRepo.save(category);

		Incident incidentForKeyboard = new Incident();
		incidentForKeyboard.setCategoryCode(category.getCode());
		incidentForKeyboard.setTitle("Keyboard problem");
		incidentForKeyboard.setDescription("Keyboard is not working");

		Incident incident = new Incident();
		incident.setCategoryCode(category.getCode());
		incident.setTitle("Mouse problem");
		incident.setDescription("mouse is not working");

		List<Incident> incidents = new ArrayList<>();
		incidents.add(incidentForKeyboard);
		incidents.add(incident);

		incidentReposatiory.saveAll(incidents);

		// when - action or behavior
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/incident/"));
		// then -verify output
		response.andDo(print()).andExpect(jsonPath("$.[0].title", is(incidentForKeyboard.getTitle())))
				.andExpect(jsonPath("$.[1].title", is(incident.getTitle())));
	}

	// get all incident by user_id
	@Test
	public void gevenIncident_whenGetAllIncidentByUser_Id_thenReturnSavedIncident() throws Exception {

		// given - precondition

		// Create category
		Category category = new Category("software", "software@33");
		categoryRepo.save(category);
		// Create User
		User user = new User("akash", "akash");
		userRepository.save(user);
		// Create Incident
		Incident incident1 = new Incident();
		incident1.setTitle("Slack");
		incident1.setUser(user);

		Incident incident2 = new Incident();
		incident2.setTitle("Slackk");
		incident2.setUser(user);

		List<Incident> incidents = new ArrayList<>();
		incidents.add(incident1);
		incidents.add(incident2);

		incidentReposatiory.saveAll(incidents);

		// when - action or behavior
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders
				.get("/incident/byuser/{user_id}", user.getUserId()).param("pageNumber", "0").param("pageSize", "2"));

		// then - verify output
		response.andDo(print()).andExpect(jsonPath("$.[0].title", is(incident1.getTitle())))
				.andExpect(jsonPath("$.[1].title", is(incident2.getTitle())));

	}

	// get incident by Category Code
	@Test
	public void gevenIncident_whenGetAllIncidentByCategoryCode_thenReturnSavedIncident() throws Exception {

		Category category = new Category("hardware", "HARDWARE@33");
		categoryRepo.save(category);

		Incident incident = new Incident();
		incident.setCategoryCode(category.getCode());
		incident.setTitle("Keyboard problem");
		incident.setDescription("Keyboard is not working");
		incident.setCategoryCode(category.getCode());
		incidentReposatiory.save(incident);
		// when - action or behavior
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders
				.get("/incident/bycode/{code}", category.getCode()).param("pageNumber", "0").param("pageSize", "2"));
		// then - verify output
		response.andDo(print()).andExpect(jsonPath("$.[0].title", is(incident.getTitle())))
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

}
