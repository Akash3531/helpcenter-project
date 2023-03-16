package com.Category;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpCenter.CategoryApplication;
import com.helpCenter.Incident.reposatiory.IncidentReposatiory;
import com.helpCenter.category.dtos.RequestCategoryDto;
import com.helpCenter.category.dtos.UpdateCategoryDto;
import com.helpCenter.category.entity.Category;
import com.helpCenter.category.repository.CategoryRepo;

@ContextConfiguration(classes = CategoryApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", password = "password", roles = "ADMIN")
class CategoryIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ObjectMapper objectMapper;
    @Autowired
    private IncidentReposatiory incidentReposatiory;
	@Test
	void contextLoads() {

	}
	
	@BeforeEach
	void setUp() {
		incidentReposatiory.deleteAll();
		categoryRepo.deleteAll();	
	}

	// CREATE CATEGORY
	@Test
	public void givenCategoryObject_whenCreateCategory_thenReturnStatus() throws Exception {
		// given - precondition or setup
		RequestCategoryDto category = new RequestCategoryDto("Hardware", "hardware8@");
		// when - action or behavior that we are going test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/category/")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(category)));
		// then - verify the result or output using assert statements
		response.andDo(print()).andExpect(MockMvcResultMatchers.status().isCreated());

	}

	// UPDATE CATEGORY
	@Test
	public void givenUpdatedCategory_whenUpdateCategory_thenReturnUpdateCategoryObject() throws Exception {
		// given - precondition or setup
		Category category = new Category("software", "SOFTWARE5@");
		categoryRepo.save(category);
		UpdateCategoryDto updateCategory = new UpdateCategoryDto("File", "Documents@2");
		Category fromDb = categoryRepo.findByName("File");
		assertSame(null, fromDb);
		// when - action or behavior
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/category/{code}", category.getCode())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateCategory)));
		// then - verify the output
		response.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());

	}

	// GET CATEGORIY BY NAME
	@Test
	public void givenCategoryName_whenGetCategoryByName_thenReturnCategoryObject() throws Exception {
		// given - precondition or setup
		Category category = new Category("procurement", "PROCUREMENT#2");
		categoryRepo.save(category);
		// when - action or the behavior that we are going test
		ResultActions response = mockMvc.perform(get("/category/{code}", category.getCode()));
		// then - verify the output
		response.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.name", is(category.getName())))
				.andExpect(jsonPath("$.code", is(category.getCode())));

	}

	// GET all category
	@Test
	public void givenCategoryObject_whenCreateCategory_thenReturnSavedCategory() throws Exception {
		// given - precondition or setup
		List<Category> categories = new ArrayList<>();
		Category category = new Category("mouse", "mouse5@");
		Category category1 = new Category("keyboard", "keyboard6@");
		categories.add(category);
		categories.add(category1);
		categoryRepo.saveAll(categories);
		// when - action or behavior that we are going test
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/category/"));
		// then - verify the result or output using assert statements
		response.andDo(print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.[0].name", is(category.getName())))
				.andExpect(jsonPath("$.[1].name", is(category1.getName())));

	}
	
	

}
