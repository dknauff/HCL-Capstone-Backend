package com.hcl.service;


import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hcl.model.Category;
import com.hcl.model.Product;
import com.hcl.repo.ProductRepo;

@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CategoryService categoryService;
	
	@Test
	public void testAutowired() {
		assertNotNull(productService);	
		assertNotNull(productRepo);
		assertNotNull(categoryService);
	}
	
	@Test
	public void testAddProduct() {		
		Category categoryTest = new Category(null, "test", null);
		categoryService.addCategory(categoryTest);
		Product product1 = new Product(901L, "Guitar XYZ", "Instrument with Strings", 999.99, categoryTest, null, null );

		assertNotNull(productService.addProduct(product1)); // Passed
		
	} 
	
	@Test
	public void testFindAllProducts() {
		fail("Test not yet implemented");
	}
	
	@Test
	public void testFindProductById() {
		fail("Test not yet implemented");
	}
	
	@Test
	public void testUpdateProduct() {
		fail("Test not yet implemented");
	}
	
	@Test public void testDeleteProduct() {
		fail("Test not yet implemented");
	}
}
