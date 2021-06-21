package com.hcl.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hcl.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Long> {
	@Query(nativeQuery = true, value = "SELECT c.* FROM cart c, user u WHERE c.cart_id = u.cart_id and u.id=?1")
	public Cart findByUser(Long uid);
}
