package com.hcl.service;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import com.hcl.model.Role;
import com.hcl.model.Payment;
//import com.hcl.model.User;
import com.hcl.repo.PaymentRepo;
import com.hcl.repo.RoleRepo;
import com.hcl.repo.UserRepo;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepo paymentRepo;


	@Override
	public boolean createPayment(Payment) {
		if (payment == null) {
			return false;
		}
		//payment.setPassword(bCrypt.encode(user.getPassword()));
		// Set users role to corresponding roles in the database
		// Given only the name of the roles
		HashSet<Role> map = user.getRoles().stream().map(x -> roleRepo.findByName(x.getName()))
				.collect(Collectors.toCollection(HashSet::new));
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
