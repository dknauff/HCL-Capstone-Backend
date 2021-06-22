package com.hcl.service;

import java.util.List;

import com.hcl.model.Cart;
import com.hcl.model.Order;
import com.hcl.model.User;

public interface OrderService {

	boolean createOrder(Order order, User user);
	
	Order generateOrder(Cart cart);
	
	Order getById(Long id);
	
	boolean updateOrder(String status, Long orderId);
	
	void deleteById(Long id);

	Order findOrderById(User user, Long orderId);
	
	List<Order> findAllOrdersByUser(User user);
	
}
