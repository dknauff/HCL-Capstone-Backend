package com.hcl.service;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

	@BeforeEach
	public void seedData() {
		Category categoryTest = new Category(null, "test", true, null);
		categoryService.addCategory(categoryTest);
		Product productTest1 = new Product(901L, "Neon Guitar XYZ", "Instrument with Strings", 999.99, true, categoryTest,
				null, null);
		Product productTest2 = new Product(902L, "Red Guitar XYZ", "Instrument with Strings", 999.99, true, categoryTest,
				null, null);
		Product productTest3 = new Product(903L, "Black Guitar XYZ", "Instrument with Strings", 999.99, true, categoryTest,
				null, null);
		productRepo.save(productTest1);
		productRepo.save(productTest2);
		productRepo.save(productTest3);

	}

	@Test
	public void testAutowired() {
		assertNotNull(productService);
		assertNotNull(productRepo);
		assertNotNull(categoryService);
	}

	@Test
	public void testAddProduct() { // Passed
		Category categoryTestAdd = new Category(null, "test", true, null);
		categoryService.addCategory(categoryTestAdd);
		Product product1 = new Product(1L, "Guitar XYZ", "Instrument with Strings", 999.99, true, categoryTestAdd, null,
				null);

		assertNotNull(productService.addProduct(product1));
	}

	@Test
	public void testFindAllProducts() {
		// fail("Test not yet implemented");

		List<Product> list = productService.findAllProducts();

		assertTrue(list.contains(productService.findProductById(901L)));
		assertTrue(list.contains(productService.findProductById(902L)));
		assertTrue(list.contains(productService.findProductById(903L)));

	}

	@Test
	public void testFindProductById() {
		fail("Test not yet implemented");
	}

	@Test
	public void testUpdateProduct() {
		fail("Test not yet implemented");
	}

	@Test
	public void testDeleteProduct() {
		//fail("Test not yet implemented");
		Product product = productService.findProductById(901L);
		
		assertNotNull(product);
		
		productService.deleteProduct(901L);
		
		assertNull(product = productService.findProductById(901L));
		//assertNotNull(productService.findProductById(902L));
	}
}
