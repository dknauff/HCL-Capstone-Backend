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
import com.hcl.model.Order;
import com.hcl.repo.OrderRepo;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
	ObjectMapper mapper;
	@Autowired
	private MockMvc mockMvc;
	private Order order;
	@Autowired
	private OrderRepo orderRepo;

	@BeforeEach
	public void contextLoads() {
		orderRepo.save(new Order(1l, 0, 0, 0, "", null, null));

		mapper = new ObjectMapper();
	}

	@Test
	public void testAddOrder() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) post("/orders").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(order)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value())));
	}

	@Test
	public void testGetAllOrders() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) get("/orders").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(order)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

	@Test
	public void testGetAllOrdersById() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) get("/orders/{id}").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(order)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

	@Test
	public void testUpdateOrder() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) put("/orders/{id}").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(order)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

}
