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

import com.hcl.model.Cart;
import com.hcl.model.User;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@Transactional
class CartRepoTest {

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private UserRepo userRepo;

	@BeforeEach
	public void setUp() {
		Cart cart = new Cart();
		User user = new User();
		user.setUsername("valid");
		user.setPassword("valid123");
		try {
			user = userRepo.save(user);
		} catch (Exception e) {
			// TO BE LOGGED
			fail(e.getMessage());

		}
		cart.setUser(user);
		cartRepo.save(cart);
	}

	@Test
	// Testing find all, find by id and find by username
	public void testFindCart() {
		User user = userRepo.findByUsername("valid").orElse(null);
		assertNotNull(user);

		Cart cart = cartRepo.findByUser(user);
		assertNotNull(cart);

		Cart newCart = cartRepo.findById(cart.getCartId()).orElse(null);
		assertNotNull(newCart);
	}

	@Test
	public void testUpdateCart() {
		Cart cart = cartRepo.findById(1L).orElse(null);
		assertNotNull(cart);

		cart.setNumCartItems(97);
		try {
			cartRepo.save(cart);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		cart = null;
		cart = cartRepo.findById(1L).orElse(null);
		assertTrue(cart.getNumCartItems() == 97);
		
		cart.setTotalCost(24);
		try {
			cartRepo.save(cart);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		cart = null;
		cart = cartRepo.findById(1L).orElse(null);
		assertTrue(cart.getTotalCost() == 24);
	}

	@Test
	public void testDeleteCart() {
		cartRepo.deleteById(1L);
		assertFalse(cartRepo.findById(1L).isPresent());
	}
}