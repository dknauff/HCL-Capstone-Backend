package com.hcl.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.model.Cart;
import com.hcl.model.CartItem;
import com.hcl.model.Product;
import com.hcl.model.User;
import com.hcl.repo.CartItemRepo;
import com.hcl.repo.CartRepo;
import com.hcl.repo.ProductRepo;
import com.hcl.repo.UserRepo;

@Service
@Transactional
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private CartItemRepo cartItemRepo;

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public boolean createCart(User user) {
		if (user == null)
			return false;
		if (cartRepo.findByUser(user.getId()) != null)
			return false;
		Cart cart = new Cart();
		cart.setUser(user);
		cart.setNumCartItems(0);
		user.setCart(cart);
		userRepo.save(user);
//		cartRepo.save(cart);

		return true;
	}

	@Override
	public Cart findCartByUser(User user) {
		if (user == null)
			return null;
		Cart cart = cartRepo.findByUser(user.getId());
		if(cart == null) return null;
		return cartRepo.findById(cart.getCartId()).orElse(null);
	}

	@Override
	public boolean updateCart(User user, Long productId, int itemQty) {
		if (user == null || itemQty <= 0 || productId == null)
			return false;
		Cart cart = cartRepo.findByUser(user.getId());
//		System.out.println(cart);
		if (cart == null)
			return false;
		// If the total number of items in the cart surpasses MAX_VALUE of an integer
		// need to return false
		if (Integer.MAX_VALUE - itemQty <= cart.getNumCartItems())
			return false;
		Product product = productRepo.findById(productId).orElse(null);
		if (product == null)
			return false;

		CartItem itemUpdate = cartItemRepo.findByCartAndProduct(cart, product).orElse(null);

		if (itemUpdate == null) {
			itemUpdate = new CartItem();
			itemUpdate.setProduct(product);
			itemUpdate.setCart(cart);
			itemUpdate.setItemQty(itemQty);
			cart.setNumCartItems(itemQty + cart.getNumCartItems());
		} else {
			double check = (double) itemUpdate.getItemQty() + itemQty;
			if (check > (double) Integer.MAX_VALUE) {
				return false;
			} else {
				itemUpdate.setItemQty((int) check);
				cart.setNumCartItems(itemQty + cart.getNumCartItems());
			}
		}
		cartItemRepo.save(itemUpdate);
		cartRepo.save(cart);
		return true;
	}

	@Override
	public boolean deleteCart(User user) {
		if (user == null)
			return false;
		Cart cart = cartRepo.findByUser(user.getId());
		if (cart == null)
			return false;
		cartItemRepo.findAllByCart(cart).forEach(x -> cartItemRepo.delete(x));
		cartRepo.delete(cart);
		user.setCart(null);
		userRepo.save(user);
		return true;
	}

	@Override
	public boolean deleteCartItems(User user, Long productId, int itemQty) {
		if (user == null || itemQty <= 0)
			return false;
		Cart cart = cartRepo.findByUser(user.getId());
		if (cart == null)
			return false;
		Product product = productRepo.findById(productId).orElse(null);
		if (product == null)
			return false;

		CartItem itemUpdate = cartItemRepo.findByCartAndProduct(cart, product).orElse(null);

		if (itemUpdate == null)
			return false;
		if (itemQty >= itemUpdate.getItemQty()) {
			cart.setNumCartItems(cart.getNumCartItems() - itemUpdate.getItemQty());
			cartItemRepo.delete(itemUpdate);
			cartRepo.save(cart);
			return true;
		}
		itemUpdate.setItemQty(itemUpdate.getItemQty() - itemQty);
		cart.setNumCartItems(cart.getNumCartItems() - itemQty);
		cartItemRepo.save(itemUpdate);
		cartRepo.save(cart);
		return true;
	}

	@Override
	public int numberItemsCart(User user) {
		if (user == null)
			return 0;
		Cart cart = cartRepo.findByUser(user.getId());
		if (cart == null)
			return 0;
		return cart.getNumCartItems();
	}

	@Override
	public List<CartItem> itemsInCart(User user) {
		if (user == null)
			return null;
		Cart cart = cartRepo.findByUser(user.getId());
		if (cart == null)
			return null;
		return cartItemRepo.findAllByCart(cart);
	}

}
