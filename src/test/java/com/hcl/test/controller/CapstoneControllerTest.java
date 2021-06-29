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
import com.hcl.model.Product;
import com.hcl.repo.ProductRepo;
import com.hcl.service.ProductService;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class CapstoneControllerTest {
	ObjectMapper mapper;
	@Autowired
	private MockMvc mockMvc;
	private Product product;
	@Autowired
	private ProductRepo productRepo;

	@BeforeEach
	public void contextLoads() {
		productRepo.save(new Product(1l, "Album", "The Beatles", 12, null, null, null));

		mapper = new ObjectMapper();
	}

	@Test
	public void testAddProduct() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) post("/product").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(product)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value())));
	}

	@Test
	public void testGetAllProducts() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) get("/products").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(product)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

	@Test
	public void testGetAllProductsById() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) get("/products/{id}").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(product)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

	@Test
	public void testUpdateProduct() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) put("/product/{id}").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(product)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

	@Test
	public void testDeleteProduct() throws Exception {
		mockMvc.perform((RequestBuilder) ((ResultActions) delete("/delete/{id}").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(product)))
						.andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value())));
	}

}
