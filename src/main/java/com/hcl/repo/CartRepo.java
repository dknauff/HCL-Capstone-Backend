package com.hcl.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hcl.model.Cart;
import com.hcl.model.User;

public interface CartRepo extends JpaRepository<Cart, Long> {
//	@Query(nativeQuery = true, value = "SELECT c.* FROM cart c, user u WHERE c.cart_id = u.cart_id and u.id=?1")
	public Cart findByUser(User user);
	
	@Modifying
	@Query("delete from CartItem ci where ci.cartId = ?1")
	public void deleteChildren(Long cartId);
	
	@Modifying
	@Query("delete from CartItem ci where ci.cartItemId = ?1")
	public void deleteChild(Long cartItemId);
}
