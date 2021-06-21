package com.hcl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.model.Order;
import com.hcl.model.User;
import com.hcl.repo.OrderRepo;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	@Override
	public boolean createOrder(Order order, User user) {
		
		if(user == null)
			return false;
		if(order == null)
			return false;
		
		order.setUser(user);
		
		return true;
	}
	
	
	public Order getById(Long id) {
		
		return orderRepo.findById(id).orElse(null);
		
	}

	
	public boolean updateOrder(String status, Long orderId) {
		
		Order order = orderRepo.findById(orderId).orElse(null);
		
		if(order == null)
			return false;
		
		order.setOrderStatus(status);
		
		orderRepo.save(order);
		
		return true;
		
	}

	
	public void deleteById(Long id) {
		
		orderRepo.deleteById(id);
		
	}


	@Override
	public Order findOrderById(User user, Long orderId) {
		
		if(user == null)
			return null;
		
		Order order = orderRepo.findById(orderId).orElse(null);
		
		if(order == null)
			return null;
		
		return order;
	}


	@Override
	public List<Order> findAllOrdersByUser(User user) {

		return orderRepo.findAllOrdersByUser(user);
		
		
	}




}