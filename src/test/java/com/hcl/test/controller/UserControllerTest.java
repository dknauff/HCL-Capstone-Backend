package com.hcl.test.controller;

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
import com.hcl.model.User;
import com.hcl.repo.UserRepo;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	ObjectMapper mapper;
	@Autowired
	private MockMvc mockMvc;
	private User user;
	@Autowired
	private UserRepo userRepo;

	@BeforeEach
	public void contextLoads() {
		userRepo.save(new User(1L, "daniel", "1234", "1234", null, null, null, null ));

		mapper = new ObjectMapper();
	}

	@Test
	public void testCreateUser() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(user)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value())));
	}

}
