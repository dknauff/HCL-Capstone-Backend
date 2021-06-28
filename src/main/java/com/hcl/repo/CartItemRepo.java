package com.hcl.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.model.Cart;
import com.hcl.model.CartItem;
import com.hcl.model.Product;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
	public List<CartItem> findAllByCart(Cart cart);
	public Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
