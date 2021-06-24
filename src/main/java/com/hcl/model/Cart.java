package com.hcl.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;

//  TO BE IMPLEMENTED
//	@NotNull
//	@Column(scale = 2)
//	private double totalCost;
	
	@NotNull
	@Min(value = 1, message = "Cannot have a cart with no items")
	private int numCartItems;
	
	@OneToOne(mappedBy = "cart")
	private User user;
	
	//Maybe requires fetchtype.eager to function correctly
	@OneToMany(mappedBy = "cartItemId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<CartItem> cartItems;

}
