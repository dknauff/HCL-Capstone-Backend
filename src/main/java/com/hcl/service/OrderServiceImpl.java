package com.hcl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.model.Order;
import com.hcl.repo.OrderRepo;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	
	public void create(Order order) {
		
		orderRepo.save(order);
		
	}

	
	public Order getById(Long id) {
		
		return orderRepo.findById(id).orElse(null);
		
	}

	
	public void update(Order order) {
		
		orderRepo.save(order);
		
	}

	
	public void deleteById(Long id) {
		
		orderRepo.deleteById(id);
		
	}

}