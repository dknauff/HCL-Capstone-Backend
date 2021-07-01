package com.hcl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.auth.GetAuth;
import com.hcl.model.Payment;
import com.hcl.model.User;
import com.hcl.service.PaymentService;
import com.hcl.service.UserService;

//@CrossOrigin("http://localhost:4200/")

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private GetAuth authState;
	@Autowired
	private UserService userService;

	@GetMapping("/payments")
	public ResponseEntity<?> getAllPayment() {
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		List<Payment> payments = paymentService.findAllPaymentsByUser(user);
		logger.info("All payment methods have been retrieved.");
		return payments.isEmpty() ? new ResponseEntity<String>("No payment methods found.", HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<Payment>>(payments, HttpStatus.OK);
	}

	@PostMapping("/payments")
	public ResponseEntity<?> createPayment(@RequestBody Payment payment) {

		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		boolean didCreate = paymentService.createPayment(user, payment);
		logger.info("A new payment method has been created.");
		return didCreate ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	// Single item
	@GetMapping("/payments/{id}")
	public ResponseEntity<?> onePayment(@PathVariable("id") Long paymentMethodId) {
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Payment paymentMethod = paymentService.findPaymentById(user, paymentMethodId);
		logger.info("A payment method with id: {} has been found", paymentMethodId);
		return paymentMethod == null ? new ResponseEntity<String>("No payment method found by ID", HttpStatus.NOT_FOUND)
				: new ResponseEntity<Payment>(paymentMethod, HttpStatus.OK);

	}

	@PutMapping("/payments/{id}")
	public ResponseEntity<?> updatePayment(@RequestBody Payment newPayment, @PathVariable("id") Long paymentMethodId) {
		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		boolean didUpdate = paymentService.updatePayment(user, paymentMethodId, newPayment);
		logger.info("The payment method with id: {} has been updated succesfully.", paymentMethodId);
		return didUpdate ? new ResponseEntity<String>("Did update successful!", HttpStatus.ACCEPTED)
				: new ResponseEntity<String>("update unsuccessful!", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletePaymentById(@PathVariable("id") Long paymentMethodId) {

		User user = userService.findByUsername(authState.getAuth().getName());
		if (user == null) {
			logger.warn("There is no user");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		boolean didDelete = paymentService.deletePaymentById(user, paymentMethodId);
		logger.warn("The payment method with id: {} has been deleted", paymentMethodId);
		return didDelete ? new ResponseEntity<String>("Delete successful!", HttpStatus.OK)
				: new ResponseEntity<String>("Delete unsuccessful!", HttpStatus.BAD_REQUEST);
	}

}
