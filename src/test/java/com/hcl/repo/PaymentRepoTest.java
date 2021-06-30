package com.hcl.repo;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.hcl.model.User;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@Transactional
class PaymentRepoTest {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PaymentRepo paymentRepo;

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
	}

	@Test
	// Testing user creation, constraints, etc...
	public void testSaveUser() {
	}

	@Test
	// Testing find all, find by id and find by username
	public void testFindUser() {
	}

}