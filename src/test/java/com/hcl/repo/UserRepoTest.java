package com.hcl.repo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.hcl.model.Role;
import com.hcl.model.User;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@Transactional
class UserRepoTest {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@BeforeEach
	public void setUp() {
		Role role = new Role();
		role.setName("Role");
		try {
			roleRepo.save(role);
		} catch (Exception e) {
			System.out.println("Log error" + e.getMessage());
			fail();
		}
		
		User user = new User();
		user.setUsername("user1");
		user.setPassword("user1");
		HashSet<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		try {
			userRepo.save(user);
		} catch (Exception e) {
			System.out.println("Log error" + e.getMessage());
			fail();
		}
	}

	@Test
	// Testing user creation, constraints, etc...
	public void testSaveUser() {
		User user = null;
		try {
			user = userRepo.save(new User());
			fail("Saving an empty user should throw error!");
		} catch (Exception e) {
			// SHOULD LOG exception?
			// EXCEPTION SHOULD OCCUR SINCE EMPTY USERNAME AND PASSWORD INVALID
			System.out.println("\n" + e.getMessage() + "\n");
		}

		assertNull(user);

		user = new User();
		user.setPassword("validUsername");
		user.setUsername("validPassword");
		try {
			user = userRepo.save(user);
		} catch (Exception e) {
			// SHOULD NOT FAIL
			fail(e.getMessage());
		}

		assertNotNull(user);
	}

	@Test
	// Testing find all, find by id and find by username
	public void testFindUser() {
		assertTrue(userRepo.findAll().size() == 1);

		User user = userRepo.findById(1L).orElse(null);
		assertNotNull(user);

		assertTrue(user.getUsername() == "user1");
		user = userRepo.findByUsername("user1").orElse(null);
		assertNotNull(user);
	}
	
	@Test
	public void testFindRole() {
		assertTrue(roleRepo.findAll().size()>0);
	}

}
