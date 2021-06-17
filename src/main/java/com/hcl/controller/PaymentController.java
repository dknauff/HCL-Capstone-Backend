package com.hcl.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.model.Payment;
import com.hcl.repo.PaymentRepo;
import com.hcl.service.PaymentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("http://localhost:4200/")

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {
	
	@Autowired
	private PaymentRepo paymentRepo;

//	public PaymentService getPaymentService() {
//		return paymentService;
//	}
//
//	public void setPaymentService(PaymentService paymentService) {
//		this.paymentService = paymentService;
//	}

	//
	
	  @GetMapping("/payments")
	  List<Payment> getAllPayment() {
	    return paymentRepo.findAll();
	  }
	  // end::get-aggregate-root[]

	  @PostMapping("/payments")
	  Payment createPayment(@RequestBody Payment payment) {
	    return paymentRepo.save(payment);
		//paymentRepoRepo.save(employee); 
		//return payment;
	  }

	  // Single item
	  @GetMapping("/payments/{id}")
	  Payment onePayment(@PathVariable Long paymentMethodId) {
	    
	    return paymentRepo.findById(paymentMethodId)
	    	.orElseThrow(null);
	      //.orElseThrow(() -> new PaymentNotFoundException(paymentMethodId));
	  }

	  @PutMapping("/payments/{id}")
	  Payment updatePayment(@RequestBody Payment newPayment, @PathVariable Long paymentMethodId) {	    
//	    return paymentRepo.findById(paymentMethodId);
//	      .map(payment -> {
//	        employee.setName(newPayment.getName());employee.setRole(newPayment.getRole());
	        return paymentRepo.save(newPayment);
//	      });
//	      .orElseGet(() -> {
//	        newPayment.setPaymentMethod(paymentMethodId);
//	        return paymentRepo.save(newPayment);
//	      });
	  }
	  
	  
//	  @RequestMapping("/student/info")
//	  @RequestMapping(method = RequestMethod.PUT)
//	  public @ResponseBody String updateStudent(@RequestParam(value = "stdName")String stdName){
//	      LOG.info(stdName);
//	      return "ok";
//	  }
	  
	  

	  @DeleteMapping("/payments/{paymentMethodId}")
	  void deletepayment(@PathVariable Long paymentMethodId) {
		  paymentRepo.deleteById(paymentMethodId);
	  }
	}	
	

	

