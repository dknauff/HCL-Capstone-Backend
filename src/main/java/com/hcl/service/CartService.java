package com.hcl.service;

import java.util.List;

import com.hcl.model.Cart;
import com.hcl.model.CartItem;
import com.hcl.model.User;

public interface CartService {

	public boolean createCart(User user);
	public Cart findCartByUser(User user);
	public List<CartItem> itemsInCart(User user);
	public int numberItemsCart(User user);
	public boolean updateCart(User user, Long productId, int itemQty);
	public boolean deleteCart(User user);
	public boolean deleteCartItems(User user, Long productId, int itemQty);
	}
