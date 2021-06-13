package com.hcl.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.model.User;
import com.hcl.repo.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		User user = userRepo.findByUsername(username).orElse(null);
		
		if (user == null) throw new UsernameNotFoundException(username);
		Set<GrantedAuthority> grantedAuth = 
				user.getRoles()
				.stream().map(x -> new SimpleGrantedAuthority(x.getName()))
				.collect(Collectors.toCollection(HashSet::new));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), 
				user.getPassword(), grantedAuth);
	}
}
