package com.hcl.service;

import java.util.List;

import com.hcl.model.*;


public interface PaymentService {

	public boolean createPayment(User user, Payment payment);
	public List<Payment> findAllPaymentsByUser(User user);
	public Payment findPaymentById(User user, Long paymentMethodId);
	public boolean updatePayment(User user, Long paymentMethodId, Payment payment);
	public boolean deletePaymentById(User user, Long paymentMethodId);
	
}


