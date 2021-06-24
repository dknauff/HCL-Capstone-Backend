package com.hcl.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long orderId;
	
	@NotNull
	@Column(scale = 3)
	private double taxRate = 0; // Setting default taxrate to 0%
	
	@NotNull
	@Column(scale = 2)
	private double totalCost;
	
	@NotNull
	@Min(value = 1, message = "Cannot have a order with no items")
	private int totalNumItems;
	
	@NotNull
	private String orderStatus;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id", nullable = false)
	@JsonIgnore
	private User user;
	
	// May need to change fetchtype to eager in order to return json properly
	@OneToMany(mappedBy = "orderItemId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<OrderItem> orderItems;
}
