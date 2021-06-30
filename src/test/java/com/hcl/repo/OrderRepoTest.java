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

import com.hcl.model.Order;
import com.hcl.model.User;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@Transactional
class OrderRepoTest {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private OrderRepo orderRepo;

	@BeforeEach
	public void setUpFindUser() {
		User user = new User();
		user.setUsername("user1");
		user.setPassword("user1");
		try {
			userRepo.save(user);
		} catch (Exception e) {
			System.out.println("Log error" + e.getMessage());
		}
		Order order = new Order();
		order.setUser(user);
		order.setTaxRate(0);
		order.setTotalCost(0);
		order.setOrderStatus("");
		order.setTotalNumItems(1);
		try {
			orderRepo.save(order);
		}catch (Exception e) {
			System.out.println("Log this error" + e.getMessage());
			fail();
		}
	}

	@Test
	public void testFindOrder() {
		assertNotNull(orderRepo.findById(1L).orElse(null));
	}
	
	@Test
	public void testUpdateOrder() {
		Order order = orderRepo.findById(1L).orElse(null);
		assertNotNull(order);
		assertFalse(order.getOrderStatus().equals("Order status changed"));
		
		order.setOrderStatus("Order status changed");
		orderRepo.save(order);
		order = orderRepo.findById(1L).orElse(null);
		assertNotNull(order);
		assertTrue(order.getOrderStatus().equals("Order status changed"));
	}

}