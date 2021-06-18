package com.hcl.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

}