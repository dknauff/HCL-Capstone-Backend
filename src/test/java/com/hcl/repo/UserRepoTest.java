package com.hcl.repo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.hcl.model.User;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class UserRepoTest {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Test
	public void testSaveUser() {
		User user = null;
		try {
			user = userRepo.save(new User());
		} catch (Exception e) {
			// SHOULD LOG exception?
			// EXCEPTION SHOULD OCCUR SINCE EMPTY USERNAME AND PASSWORD INVALID
			System.out.println("\n" + e.getMessage() + "\n");
		}
		assertNull(user);

		user = new User();
		user.setPassword("shouldBeLegal");
		user.setUsername("ShouldBeLegal");
		try {
			user = userRepo.save(user);
		} catch (Exception e) {
			// SHOULD NOT FAIL
			fail(e.getMessage());
		}

		assertNotNull(user);
	}
	@Before(value = "testFindUser")
	public void setUpFindUser() {
		User user = new User();
		user.setUsername("user1");
		user.setPassword("user1");
		userRepo.save(user);
	}
	@Test
	public void testFindUser() {
		assertTrue(userRepo.findAll().size() == 1);
		User user = userRepo.findById(1L).orElse(null);
		assertNotNull(user);
		assertTrue(user.getUsername() == "user1");
	}

}
