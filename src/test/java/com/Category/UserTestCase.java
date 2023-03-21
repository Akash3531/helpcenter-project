package com.Category;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpCenter.CategoryApplication;
import com.helpCenter.Incident.reposatiory.IncidentReposatiory;
import com.helpCenter.user.dto.RequestUserDTO;
import com.helpCenter.user.dto.UpdateUserDto;
import com.helpCenter.user.entity.User;
import com.helpCenter.user.repository.UserRepository;

@ContextConfiguration(classes = CategoryApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", password = "password", roles = "ADMIN")
public class UserTestCase {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	IncidentReposatiory incidentReposatiory;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {

	}

	@BeforeEach
	void setup() {
		incidentReposatiory.deleteAll();
		userRepository.deleteAll();
	}

	// CREATE User
	@Test
	public void givenUserObject_whenCreateUser_thenReturnStatusCreated() throws Exception {

		// given - precondition or setup
		RequestUserDTO userDto = new RequestUserDTO("akash", "akash", "prabhjot");

		// when - action or behavior that we are going test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/user/")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDto)));

		// then - verify the result or output using assert statements
		response.andDo(print()).andExpect(MockMvcResultMatchers.status().isCreated());
	}

	// update user
	@Test
	public void givenUpdatedUser_whenUpdateUser_thenReturnUpdateUserObject() throws Exception {
		// given - precondition or setup
		RequestUserDTO userDto = new RequestUserDTO("akash", "akash", "prabhjot");
		User user = new User(userDto);
		userRepository.save(user);
		UpdateUserDto updateUserDto = new UpdateUserDto("amit", "amit");
		User byuserName = userRepository.findByuserName("amit");
		assertSame(null, byuserName);
		// when - action or behavior that we are going test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/user/{userName}", "akash")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateUserDto)));
		// then - verify the result or output
		response.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
	}

	// get user by name
	@Test
	public void givenUser_whenGetUserByName_returnUserObject() throws JsonProcessingException, Exception {
		// given - precondition
		RequestUserDTO userDto = new RequestUserDTO("akash", "akash", "prabhjot");
		User user = new User(userDto);
		userRepository.save(user);
		// when - action or behavior that we are going to test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/user/{userName}", "akash"));
		// then - verify outPut
		response.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.userName", is(user.getUserName())));
	}

	// get all users
	@Test
	public void givenUserObject_whenGetUser_returnSavedUser() throws Exception {
		// given - precondition
		List<User> add = new ArrayList<>();
		RequestUserDTO userDto = new RequestUserDTO("akash", "akash", "prabhjot");
		RequestUserDTO userDto1 = new RequestUserDTO("sonu", "sonu", "prabhjot");
		User user = new User(userDto);
		User user1 = new User(userDto1);
		add.add(user);
		add.add(user1);
		userRepository.saveAll(add);
		// then - action or behavior that we are going to test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/user/"));
		// then - verify output
		response.andDo(print()).andExpect(jsonPath("$.[0].userName", is(user.getUsername())))
				.andExpect(jsonPath("$.[1].userName", is(user1.getUsername())))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	// Delete user
	@Test
	public void givenUserObject_whenDeleteUser_returnStatus() throws Exception {
		// given -precondition
		User user = new User("akash", "akash");
		userRepository.save(user);
		// then - action or behavior that we are going to test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userName}", user.getUsername()));
		// then - verify output
		response.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
	}

}
