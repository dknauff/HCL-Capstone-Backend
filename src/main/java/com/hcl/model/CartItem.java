package com.hcl.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "cart_item")
public class CartItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
	private int itemQty;
//	private Cart cart;
	

}