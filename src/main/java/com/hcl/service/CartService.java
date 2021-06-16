package com.hcl.service;

import com.hcl.model.Cart;
import com.hcl.model.CartItem;
import com.hcl.model.User;

public interface CartService {

	public boolean createCart(User user);
	public Cart findCartByUser(User user);
	public boolean updateCart(User user, CartItem cartItem, int itemQty);
	public boolean deleteCart(User user);
	public boolean deleteCartItems(User user, CartItem cartItem, int itemQty);
	}
