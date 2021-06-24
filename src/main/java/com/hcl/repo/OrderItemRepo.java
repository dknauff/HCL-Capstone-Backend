package com.hcl.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.model.Order;
import com.hcl.model.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
	List<OrderItem> findAllByOrder(Order order);
}
