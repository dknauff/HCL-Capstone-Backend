package com.hcl.service;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hcl.model.Category;
import com.hcl.model.Product;
import com.hcl.repo.CategoryRepo;
import com.hcl.repo.ProductRepo;

@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@BeforeEach
	public void seedData() {
		Category categoryTest = new Category(null, "test", true, null);
		categoryRepo.save(categoryTest);
		Product productTest1 = new Product(null, "Neon Guitar XYZ", "Instrument with Strings", 999.99, true,
				null, categoryTest, null, null);
		Product productTest2 = new Product(null, "Red Guitar XYZ", "Instrument with Strings", 999.99, true,
				null, categoryTest, null, null);
		Product productTest3 = new Product(null, "Black Guitar XYZ", "Instrument with Strings", 999.99, true,
				null, categoryTest, null, null);

		productRepo.save(productTest1);
		productRepo.save(productTest2);
		productRepo.save(productTest3);
	}

	@Test
	public void testAutowired() {
		assertNotNull(productService);
		assertNotNull(productRepo);
		assertNotNull(categoryRepo);
	}

	@Test
	public void testFindProductById() {

		Product product = null;

		product = productService.findProductById(1L);

		System.out.println("Product name is " + product.getName());

		assertNotNull(product);
	}

	@Test
	public void testDeleteProduct() { // fail("Test not yet implemented");

		productService.deleteProduct(1L);

		Product product = productService.findProductById(1L);

		assertNull(product);

	}

	@Test
	public void testUpdateProduct() {

		Product product = productService.findProductById(1L);

		assertEquals(true, product.isInstock());
		assertEquals(999.99, product.getPrice());

		product.setInstock(false);
		product.setPrice(555.55);

		productService.updateProduct(product, 1L);

		Product productTestUpdate = productService.findProductById(1L);

		assertNotEquals(true, productTestUpdate.isInstock());
		assertNotEquals(999.99, productTestUpdate.getPrice());

	}

	@Test
	public void testFindAllProducts() { //

		List<Product> list = productService.findAllProducts();

		assertTrue(list.contains(productService.findProductById(1L)));
		assertTrue(list.contains(productService.findProductById(2L)));
		assertTrue(list.contains(productService.findProductById(3L)));

	}

	@Test
	public void testAddProduct() {

		List<Product> list = productService.findAllProducts();
		
		for(Product prod : list) {
			System.out.println("Product name is " + prod.getName() + " price: " + prod.getPrice());
		}
		
		Category categoryTestAdd = new Category(null, "test", true, null);
		categoryRepo.save(categoryTestAdd);

		Product product1 = new Product(null, "Drums TTT", "Instrument with Sticks", 499.99, true, null, categoryTestAdd, null,
				null);

		assertNotNull(productService.addProduct(product1));

		Product productTestAdd = productService.findProductById(4L);

		assertEquals(499.99, productTestAdd.getPrice());
		
		list = productService.findAllProducts();
		
		for(Product prod : list) {
			System.out.println("\n\n\nProduct name is " + prod.getName() + " price: " + prod.getPrice());
		}
		
	}

}
