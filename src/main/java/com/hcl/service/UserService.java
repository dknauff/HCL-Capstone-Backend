package com.hcl.service;

import com.hcl.model.User;

public interface UserService {
	public boolean createUser(User user);
	public User findById(Long id);
	public User findByUsername(String username);
	public boolean updateUser(Long id, User user);
}
