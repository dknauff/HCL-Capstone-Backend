package com.hcl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.auth.GetAuth;
import com.hcl.model.Cart;
import com.hcl.model.CartItem;
import com.hcl.model.User;
import com.hcl.service.CartService;
import com.hcl.service.UserService;

//@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(path = "/cart")
@PreAuthorize("hasRole('ROLE_USER')")
public class CartController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CartService cartService;
	@Autowired
	private GetAuth authState;
	@Autowired
	private UserService userService;

	@PostMapping(path = "/create")
	public ResponseEntity<?> createUser(@RequestBody Cart cart, BindingResult errors) {
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		boolean didCreate = cartService.createCart(user);
		logger.info("New cart has been created for the user");
		return didCreate ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping()
	public ResponseEntity<?> getCart() {
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Cart cart = cartService.findCartByUser(user);
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@GetMapping(path = "/items")
	public ResponseEntity<?> getCartItems(){
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Cart cart = cartService.findCartByUser(user);
		logger.info("Cart items have been retrieved");
		return cart != null ? new ResponseEntity<List<CartItem>>(cartService.itemsInCart(user), HttpStatus.OK) : new ResponseEntity<String>("No cart found for user", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping(path = "/delete")
	public ResponseEntity<?> deleteCart() {
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		boolean didDelete = cartService.deleteCart(user);
		logger.warn("A cart has been deleted");
		return didDelete ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PutMapping(path = "/add/{id}")
	public ResponseEntity<?> addItems(@PathVariable Long id, @RequestBody int qty) {
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		logger.info("An item with id: {} has been added to cart", id);
		return cartService.updateCart(user, id, qty) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<?> deleteItems(@PathVariable Long id, @RequestBody int qty){
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		logger.warn("An item with id: {} has been deleted from cart", id);
		return cartService.deleteCartItems(user, id, qty) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}