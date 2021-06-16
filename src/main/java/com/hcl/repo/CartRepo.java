package com.hcl.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.model.Cart;
import com.hcl.model.User;

public interface CartRepo extends JpaRepository<Cart, Long> {

	public Cart findByUser(User user);
}
