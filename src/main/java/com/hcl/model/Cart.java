package com.hcl.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;
	
	@NotNull
	@Min(value = 0, message = "Cannot have a cart with less than zero items")
	private int numCartItems;
	
//	@OneToOne(mappedBy = "cart", cascade = CascadeType.PERSIST)
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name="id")
	@JsonIgnore
	private User user;
	
	//Maybe requires fetchtype.eager to function correctly
	@OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<CartItem> cartItems;
	
	public void addChild(CartItem ci) {
		this.cartItems.add(ci);
	}

	public void removeChild(CartItem ci) {
		this.cartItems.remove(ci);
	}
	
	@PreRemove
	public void removeChildren() {
		cartItems.forEach(x-> x.removeChild());
		cartItems.clear();
	}
}
