package com.hcl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcl.model.Payment;
import com.hcl.model.User;
import com.hcl.repo.PaymentRepo;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepo paymentRepo;

	@Override
	public boolean createPayment(User user, Payment payment) {
		// TODO Auto-generated method stub

		if (payment == null)
			return false;

		payment.setUser(user); // payment.setNumCartItems(0);
		paymentRepo.save(payment);

		return true;
	}

	@Override
	public List<Payment> findAllPaymentsByUser(User user) {
		// TODO Auto-generated method stub
		return paymentRepo.findAllByUser(user);
	}

	@Override
	public Payment findPaymentById(User user, Long paymentMethodId) {
		Payment payment = paymentRepo.findById(paymentMethodId).orElse(null);
		if (payment == null)
			return null;

//		if (payment.getUser() != user)
//			return null;

		return payment;
	}

	@Override
	public boolean updatePayment(User user, Long paymentMethodId, Payment payment) {
		Payment updatePayment = paymentRepo.findById(paymentMethodId).orElse(null);
		if (updatePayment == null)
			return false;

		if (updatePayment.getUser() != user)
			return false;
		payment.setPaymentMethodId(updatePayment.getPaymentMethodId());
		payment.setUser(user);
		paymentRepo.save(payment);
		return true;
	}

	@Override
	public boolean deletePaymentById(User user, Long paymentMethodId) {
		Payment deletePayment = paymentRepo.findById(paymentMethodId).orElse(null);
		if (deletePayment == null)
			return false;

		if (deletePayment.getUser() != user)
			return false;
		paymentRepo.deleteById(paymentMethodId);

		return true;
	}

}
