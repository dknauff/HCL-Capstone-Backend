package com.hcl.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
	public Role findByName(String name);
}
