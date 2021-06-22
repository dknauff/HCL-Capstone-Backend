package com.hcl.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.model.Order;
import com.hcl.model.User;

public interface OrderRepo extends JpaRepository<Order, Long> {

	List<Order> findAllByUser(User user);

}