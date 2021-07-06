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
	@Query(nativeQuery = true, value = "delete from Cart_Item ci where ci.cart_id = ?1")
	public void deleteChildren(Long cartId);
	
	@Modifying
	@Query(nativeQuery = true, value = "delete from Cart_Item ci where ci.cart_item_id = ?1")
	public void deleteChild(Long cartItemId);
}
