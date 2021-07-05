package com.hcl.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "cart_item")
public class CartItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;
	
	@NotNull
	@Min(value = 1, message = "Cannot have a cart item with less than 1 quantity")
	private int itemQty;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "cart_id", nullable = false)
	@JsonIgnore
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Cart cart;	
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Product product;
	
	@PreRemove
	public void removeChild() {
		cart.removeChild(this);
		this.cart = null;
	}

}