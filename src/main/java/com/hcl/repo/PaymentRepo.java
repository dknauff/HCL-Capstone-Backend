package com.hcl.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hcl.model.Payment;
import com.hcl.model.User;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
	//public Payment findById(Long paymentMethodId);
	public List<Payment> findAllByUser(User user);
	
}
