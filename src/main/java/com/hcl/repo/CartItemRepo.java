package com.hcl.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.model.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
