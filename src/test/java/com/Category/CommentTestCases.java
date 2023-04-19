package com.Category;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpCenter.CategoryApplication;
import com.helpCenter.Incident.entity.Incident;
import com.helpCenter.Incident.reposatiory.IncidentReposatiory;
import com.helpCenter.category.entity.Category;
import com.helpCenter.category.repository.CategoryRepo;
import com.helpCenter.comment.dto.RequestCommentDto;
import com.helpCenter.comment.entity.Comment;
import com.helpCenter.comment.reposatiory.CommentReposatiory;
import com.helpCenter.requestHandlers.entity.HandlerDetails;
import com.helpCenter.requestHandlers.entity.RequestHandler;
import com.helpCenter.user.entity.User;
import com.helpCenter.user.repository.UserRepository;

@SpringBootTest
@ContextConfiguration(classes = CategoryApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "user", password = "password", roles = "ADMIN")
public class CommentTestCases {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CommentReposatiory commentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	IncidentReposatiory incidentReposatiory;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {

	}

	@BeforeEach
	void setup() {
		commentRepository.deleteAll();
		incidentReposatiory.deleteAll();
		categoryRepo.deleteAll();
		userRepository.deleteAll();
	}

	// CREATE COMMENT
	@Test
	public void givenCommentObject_whenCreateComment_thenReturnStatus() throws Exception {

		// given - precondition or setup
		Category category = new Category();
		category.setName("software");
		category.setCode("SOFTWARE@12");
		category.setEtaInMinutes(5);
		
		User user=new User();
		user.setUserName("akash");
		user.setPassword("akash");
		user.setEmail("akash@gmail.com");
		userRepository.save(user);
		List<String> resources = new ArrayList<>();
		resources.add("akash");
		HandlerDetails detail = new HandlerDetails();
		detail.setLevel(1);
		detail.setResources(resources);
		List<HandlerDetails> details = new ArrayList<>();
		details.add(detail);
		RequestHandler requestHandler = new RequestHandler();
		requestHandler.setHandler(details);
		requestHandler.setCategory(category);
		category.setRequestHandler(requestHandler);
		categoryRepo.save(category);

		Incident incident = new Incident();
		incident.setCategory(category);
		incident.setUser(user);
		incident.setLastmailSendedTime(new Date());
		incident.setTitle("mouse problem");
		incident.setDescription("mouse is not working");
		incidentReposatiory.save(incident);

		RequestCommentDto commentDto = new RequestCommentDto();
		commentDto.setComments("please check mouse");

		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = Obj.writeValueAsString(commentDto);
		MockMultipartFile jsonFile = new MockMultipartFile("comment", "", "application/json", jsonStr.getBytes());

		MockMultipartFile file = new MockMultipartFile("image", "C:\\Users\\akash\\Pictures\\bankimage.jpg",
				MediaType.MULTIPART_FORM_DATA_VALUE, "".getBytes());
		// when - action or behavior
		ResultActions response = mockMvc
				.perform(multipart("/comment/{id}", incident.getId()).file(jsonFile).file(file));
		// then -verify result
		response.andExpect(status().isCreated());
	}

//	 GET ALL COMMENTS
	@Test
	public void givenComments_whenGetAllComments_thenReturnSavedComment() throws Exception {

		Comment comment1 = new Comment();
		comment1.setComments("keyboard is not working");

		Comment comment2 = new Comment();
		comment2.setComments("Mouse is not working");

		List<Comment> comments = new ArrayList<>();
		comments.add(comment1);
		comments.add(comment2);

		commentRepository.saveAll(comments);

		// when - action or behavior
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/comment/"));
		// then -verify output
		response.andDo(print()).andExpect(jsonPath("$.[0].comments", is(comment1.getComments())))
				.andExpect(jsonPath("$.[1].comments", is(comment2.getComments())));
	}

	// GET COMMENTS BY INCIDENT
	@Test
	public void givenComment_whenGetCommentByIncidentId_thenReturnSavedComment() throws Exception {
		// given - precondition
		Category category = new Category("hardware", "HARDWARE@33");
		categoryRepo.save(category);

		Incident incident = new Incident();
		incident.setCategoryCode(category.getCode());
		incident.setTitle("Keyboard problem");
		incident.setDescription("Keyboard is not working");
		incidentReposatiory.save(incident);

		Comment comment1 = new Comment();
		comment1.setComments("keyboard is not working");
		comment1.setIncident(incident);

		Comment comment2 = new Comment();
		comment2.setComments("Mouse is not working");
		comment2.setIncident(incident);

		List<Comment> comments = new ArrayList<>();
		comments.add(comment1);
		comments.add(comment2);
		commentRepository.saveAll(comments);
		int id = 0;
		id = incident.getId();
		System.out.println(id);
		// when - action or behavior
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/comment/{id}", id));
		// then - verify output
		response.andDo(print()).andExpect(jsonPath("$.[0].comments", is(comment1.getComments())))
				.andExpect(jsonPath("$.[1].comments", is(comment2.getComments())));
	}

}
