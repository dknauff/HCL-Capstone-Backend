package com.hcl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.auth.GetAuth;
import com.hcl.model.Order;
import com.hcl.model.User;
import com.hcl.service.OrderService;
import com.hcl.service.UserService;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private GetAuth authState;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(path = "/orders")
	public ResponseEntity<?> getAllOrders() {
		
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	
		List<Order> orders = orderService.findAllOrdersByUser(user);
		
		return orders.isEmpty() ? new ResponseEntity<String>("No orders found for user.", HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
		
	}
	
	@PostMapping(path = "/create")
	public ResponseEntity<?> createOrder(@RequestBody Order order, BindingResult errors) {
		
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		boolean didCreate = orderService.createOrder(order, user);
		
		return didCreate ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	// Single order
	@GetMapping("/orders/{id}")
	public ResponseEntity<?> oneOrder(@PathVariable("id") Long orderId) {
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Order order = orderService.findOrderById(user, orderId);
		return order == null ? new ResponseEntity<String>("No order found by ID", HttpStatus.NOT_FOUND)
				: new ResponseEntity<Order>(order, HttpStatus.OK);

	}
	
	@PutMapping("/orders/{id}")
	public ResponseEntity<?> updateOrderStatus(@RequestBody String status, @PathVariable("id") Long orderId) {
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		boolean didUpdate = orderService.updateOrder(status, orderId);
		return didUpdate ? new ResponseEntity<String>("Did update successful!", HttpStatus.ACCEPTED)
				: new ResponseEntity<String>("update unsuccessful!", HttpStatus.BAD_REQUEST);
	}
	

	
}
