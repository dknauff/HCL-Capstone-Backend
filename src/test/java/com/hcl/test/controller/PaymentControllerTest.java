package com.hcl.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.model.Payment;
import com.hcl.model.Product;
import com.hcl.repo.PaymentRepo;
import com.hcl.service.PaymentService;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class PaymentControllerTest {
	
	ObjectMapper mapper;
	@Autowired
	private MockMvc mockMvc;
	private Payment payment;
	@Autowired
	private PaymentRepo paymentRepo;

	@BeforeEach
	public void contextLoads() {
		paymentRepo.save(new Payment(1l, "credit card", 123456789, "Daniel", "Sunderland", 123, "06/21", null));

		mapper = new ObjectMapper();
	}

	@Test
	public void testAddPayment() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) post("/payments").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(payment)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value())));
	}

	@Test
	public void testGetAllPayments() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) get("/payments").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(payment)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

	@Test
	public void testGetAllPaymentsById() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) get("/payments/{id}").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(payment)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

	@Test
	public void testUpdatePayment() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) put("/payments/{id}").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(payment)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

	@Test
	public void testDeletePayment() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) delete("/delete/{id}").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(payment)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}


}
