package com.hcl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hcl.model.Role;
import com.hcl.model.User;
import com.hcl.service.RoleService;
import com.hcl.service.UserService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CapstoneRESTApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneRESTApplication.class, args);
	}

	// Generating the Roles, ROLE_USER and ROLE_ADMIN
	@Bean
	public CommandLineRunner mappingDemo(RoleService roleService, UserService userService) {
		return args -> {
			Role role = new Role();
			role.setName("ROLE_USER");
			roleService.createRole(role);
			
			role = new Role();
			role.setName("ROLE_ADMIN");
			roleService.createRole(role);
			
			User user = new User();
			user.setUsername("admin");
			user.setPassword("admin123");
			Set<Role> roleSet = new HashSet<>();
			roleSet.add(role);
			user.setRoles(roleSet);
			userService.createUser(user);
		};
	}

}
