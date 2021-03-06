package com.hcl.service;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hcl.model.Role;
import com.hcl.model.User;
import com.hcl.repo.RoleRepo;
import com.hcl.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder bCrypt;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public boolean createUser(User user) {
		if (user == null) {
			return false;
		}
		user.setPassword(bCrypt.encode(user.getPassword()));
		// Set users role to corresponding roles in the database
		// Given only the name of the roles
		HashSet<Role> map = user.getRoles().stream().filter(x -> roleRepo.findByName(x.getName())!= null).map(x -> roleRepo.findByName(x.getName()))
				.collect(Collectors.toCollection(HashSet::new));
		if(map == null || map.isEmpty())
			return false;
		user.setRoles(map);
		userRepo.save(user);
		return true;
	}

	@Override
	public User findById(Long id) {
		return userRepo.findById(id).orElse(null);
	}

	@Override
	public User findByUsername(String username) {
		return userRepo.findByUsername(username).orElse(null);
	}

	@Override
	public boolean updateUser(Long id, User user) {
		if (!userRepo.findById(id).isPresent()) {
			return false;
		}
		user.setId(id);
		userRepo.save(user);
		return true;
	}

}
