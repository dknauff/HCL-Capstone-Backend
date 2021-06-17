package com.hcl.service;

import com.hcl.model.*;


public class PaymentService {

	private Long paymentMethodId;
	private String paymentMethod;
	private Integer cardNumber;
	private String firstName;
	private String lastName;
	private Integer cvv;    
	private String expDate;	
	
	
	public PaymentService(
			Long paymentMethodId, String paymentMethod, Integer cardNumber, 
			String firstName, String lastName, Integer cvv, String expDate) {
		this.paymentMethodId = paymentMethodId;
		this.paymentMethod = paymentMethod;
		this.cardNumber = cardNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cvv = cvv;
		this.expDate = expDate;
	}
	
	public long getPaymentMethodId() {
		return paymentMethodId;
	}
	public String getPaymentMethod() {
		return paymentMethod;		
	}	
	public Integer getCardNumber() {
		return cardNumber;
	}
	public String getFirstName() {
		return firstName;
	}	
	public String getLastName() {
		return lastName;
	}
	public Integer getCvv() {
		return cvv;
	}
	public String getExpDate() {
		return expDate;
	}	
	
//	public User findById(Long id);
//	public User findByUsername(String username);	
//	public boolean createPayment();
//	public boolean updatePayment(Long paymentMethodId, Payment payment);
	
}
