package com.hcl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.model.Role;
import com.hcl.repo.RoleRepo;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public Role findRole(String roleName) {
		return roleRepo.findByName(roleName);
	}

	@Override
	public boolean createRole(Role role) {
		if(roleRepo.findByName(role.getName()) != null) {
			return false;
		}
		roleRepo.save(role);
		return true;
	}

}
