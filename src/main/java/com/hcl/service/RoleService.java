package com.hcl.service;

import com.hcl.model.Role;

public interface RoleService {
	public Role findRole(String roleName);
	public boolean createRole(Role role);
}


