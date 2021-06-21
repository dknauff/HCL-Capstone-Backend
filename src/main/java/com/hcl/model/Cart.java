package com.hcl.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;
	private int numCartItems;
	
	@OneToOne(mappedBy = "cart")
	private User user;
	
	//Maybe requires fetchtype.eager to function correctly
	@OneToMany(mappedBy = "cartItemId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<CartItem> cartItems;

}
