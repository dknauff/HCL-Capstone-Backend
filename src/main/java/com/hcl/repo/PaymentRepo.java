package com.hcl.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.model.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
	//public Payment findById(Integer paymentMethodID);
}
