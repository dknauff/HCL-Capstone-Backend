package com.hcl.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "PaymentInfo")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long paymentMethodId;
	
	private String paymentMethod;
	
	private int cardNumber;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	private int cvv;
	
	@NotNull
	private String expDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id", nullable = false)
	@JsonIgnore
	private User user;//mapping
}
