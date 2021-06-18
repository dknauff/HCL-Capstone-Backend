package com.hcl.service;

import com.hcl.model.Order;

public interface OrderService {

	void create(Order order);
	
	Order getById(Long id);
	
	void update(Order order);
	
	void deleteById(Long id);
	
}
