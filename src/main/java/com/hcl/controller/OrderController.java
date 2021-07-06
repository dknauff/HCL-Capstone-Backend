package com.hcl.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.hcl.service.CartService;
import com.hcl.service.OrderService;
import com.hcl.service.UserService;

@CrossOrigin(value = "https://capstone-front-end-react.herokuapp.com/", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping(path = "/order")
public class OrderController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private GetAuth authState;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(path = "/orders")
	public ResponseEntity<?> getAllOrders() {
		
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		List<Order> orders = orderService.findAllOrdersByUser(user);
		logger.info("All orders have been retrieved.");
		return orders.isEmpty() ? new ResponseEntity<String>("No orders found for user.", HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
		
	}
	
	@PostMapping(path = "/create")
	public ResponseEntity<?> createOrder(@RequestBody Order order, BindingResult errors) {
		
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
//		Cart cart = cartService.findCartByUser(user);
//		
//		if(cart == null)
//			return new ResponseEntity<String>("Cart is empty", HttpStatus.BAD_REQUEST);
//		
//		Order generateOrder = orderService.generateOrder(cart);
//		
//		if(generateOrder == null)
//			return new ResponseEntity<String>("Error putting in order", HttpStatus.BAD_REQUEST);
//		boolean didCreate = orderService.createOrder(generateOrder, user);
		boolean didCreate = orderService.createOrder(order, user);
		logger.info("A new order has been created.");
		return didCreate ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	// Single order
	@GetMapping("/orders/{id}")
	public ResponseEntity<?> oneOrder(@PathVariable("id") Long orderId) {
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Order order = orderService.findOrderById(user, orderId);
		logger.info("An order with id: {} has been retrieved.", orderId);
		return order == null ? new ResponseEntity<String>("No order found by ID", HttpStatus.NOT_FOUND)
				: new ResponseEntity<Order>(order, HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/orders/{id}")
	public ResponseEntity<?> updateOrderStatus(@RequestBody Order order, @PathVariable("id") Long orderId) {
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(order.getOrderStatus().isEmpty()) {
			logger.error("Cart is empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		boolean didUpdate = orderService.updateOrder(order.getOrderStatus(), orderId);
		logger.info("An order with id: {} has been updated successfully.", orderId);
		return didUpdate ? new ResponseEntity<String>("Did update successful!", HttpStatus.ACCEPTED)
				: new ResponseEntity<String>("update unsuccessful!", HttpStatus.BAD_REQUEST);
	}
	

	
}
