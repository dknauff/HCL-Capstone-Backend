package com.hcl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.auth.GetAuth;
import com.hcl.model.Cart;
import com.hcl.model.User;
import com.hcl.service.CartService;
import com.hcl.service.UserService;

//@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(path = "/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	@Autowired
	private GetAuth authState;
	@Autowired
	private UserService userService;

	@PostMapping(path = "/create")
	public ResponseEntity<?> createUser(@RequestBody Cart cart, BindingResult errors) {
//		if (errors.hasErrors()) {
//			return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
//		}

		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		boolean didCreate = cartService.createCart(user);
		return didCreate ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@DeleteMapping(path = "/delete")
	public ResponseEntity<?> deleteUser() {
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		boolean didDelete = cartService.deleteCart(user);
		return didDelete ? new ResponseEntity<>(HttpStatus.GONE) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}
}