package com.hcl.repo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.hcl.model.Category;
import com.hcl.model.Product;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@Transactional
class ProductRepoTest {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@BeforeEach
	public void setUp() {
		Category cat = new Category();
		cat.setCategoryName("Category");
		try {
			categoryRepo.save(cat);
		} catch (Exception e) {
			System.out.println("Log error" + e.getMessage());
			fail();
		}
		Product prod = new Product();
		prod.setName("Product");
		prod.setPrice(0);
		prod.setDescription("Desc");
		prod.setCategory(cat);
		try {
			productRepo.save(prod);
		} catch (Exception e) {
			System.out.println("Log error" + e.getMessage());
			fail();
		}
	}

	@Test
	public void testFindCategory() {
		assertFalse(categoryRepo.findAll().size() == 0);
		assertNotNull(categoryRepo.findById(1L).orElse(null));
	}

	@Test
	public void testUpdateCategory() {
		Category cat = categoryRepo.findById(1L).orElse(null);
		assertNotNull(cat);
		assertFalse(cat.getCategoryName().equals("New Category"));
		
		cat.setCategoryName("New Category");
		try {
		cat = categoryRepo.save(cat);
		}catch (Exception e) {
			System.out.println("Log error" + e.getMessage());
			fail();
		}
		assertNotNull(cat);
		assertTrue(cat.getCategoryName().equals("New Category"));
	}

	@Test
	// Subject to change
	public void testDeleteCategory() {
		categoryRepo.deleteById(1L);
		assertFalse(categoryRepo.findById(1L).isPresent());
	}
	@Test
	public void testFindProduct() {
		assertFalse(productRepo.findAll().size() == 0);
		assertNotNull(productRepo.findById(1L));
		assertTrue(productRepo.findAllByInstock(false).size() == 0);
		assertTrue(productRepo.findAllByInstockAndCategory(true, categoryRepo.findById(1L).orElse(null)).size() == 1);
	}

	@Test
	public void testUpdateProduct() {
		Product prod = productRepo.findById(1L).orElse(null);
		assertNotNull(prod);
		assertFalse(prod.getName().equals("New Product"));
		prod.setName("New Product");
		assertFalse(prod.getPrice() == 250);
		prod.setPrice(250);
		assertFalse(prod.getDescription().equals("New Desc"));
		prod.setDescription("New Desc");
		
		try {
		prod = productRepo.save(prod);
		}catch (Exception e) {
			System.out.println("Log error" + e.getMessage());
			fail();
		}
		assertNotNull(prod);
		assertTrue(prod.getName().equals("New Product"));
		assertTrue(prod.getPrice() == 250);
		assertTrue(prod.getDescription().equals("New Desc"));
	}
	
	@Test
	// Subject to change
	public void testDeleteProduct() {
		productRepo.deleteById(1L);
		assertFalse(productRepo.findById(1L).isPresent());
	}
	
	@Test
	public void testFindAllUserQuery() {
		assertTrue(productRepo.findAllUsersWhereCartHasProduct(1L).size() == 0);
	}

}