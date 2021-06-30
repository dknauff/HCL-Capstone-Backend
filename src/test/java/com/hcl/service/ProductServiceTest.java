package com.hcl.service;


import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
		//fail("Test not yet implemented"); // This is like a To-Do
		
		
		
		Product product1 = new Product(901L, "Guitar XYZ", "Instrument with Strings", 999.99, null, null, null );
		/*
		 * product1.setProductId(901L); product1.setName("Guitar XYZ");
		 * product1.setDescription("Instrument with Strings");
		 * product1.setPrice(999.99);
		 */
		
		productService.addProduct(product1);
		List<Product> list = productService.findAllProducts();
		assertTrue(list.contains(product1));
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
