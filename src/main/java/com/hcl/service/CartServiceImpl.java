package com.hcl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.model.Cart;
import com.hcl.model.CartItem;
import com.hcl.model.User;
import com.hcl.repo.CartItemRepo;
import com.hcl.repo.CartRepo;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private CartItemRepo cartItemRepo;

	@Override
	public boolean createCart(User user) {
		if (cartRepo.findByUser(user) != null)
			return false;
		Cart cart = new Cart();
		cart.setUser(user);
		cart.setNumCartItems(0);
		cartRepo.save(cart);

		return true;
	}

	@Override
	public Cart findCartByUser(User user) {
		return cartRepo.findByUser(user);
	}

	@Override
	public boolean updateCart(User user, CartItem cartItem, int itemQty) {
		return false;
	}

	@Override
	public boolean deleteCart(User user) {
		if (cartRepo.findByUser(user) == null)
			return false;
		cartRepo.delete(cartRepo.findByUser(user));
		return true;
	}

	@Override
	public boolean deleteCartItems(User user, CartItem cartItem, int itemQty) {

		return false;
	}

}
