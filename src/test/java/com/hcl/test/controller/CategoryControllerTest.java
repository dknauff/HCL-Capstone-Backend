package com.hcl.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.model.Category;
import com.hcl.repo.CategoryRepo;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

	ObjectMapper mapper;
	@Autowired
	private MockMvc mockMvc;
	private Category category;
	@Autowired
	private CategoryRepo categoryRepo;

	@BeforeEach
	public void contextLoads() {
		categoryRepo.save(new Category(1l, "", null));

		mapper = new ObjectMapper();
	}

	@Test
	public void testAddCategory() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) post("/categories").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(category)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value())));
	}

	@Test
	public void testGetAllCategorys() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) get("/categories").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(category)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

	@Test
	public void testGetAllCategorysById() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) get("/categories/{id}").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(category)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

	@Test
	public void testUpdateCategory() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) put("/categories/{id}").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(category)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

	@Test
	public void testDeleteCategory() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) delete("/delete/{id}").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(category)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}


}
