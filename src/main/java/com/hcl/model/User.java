package com.hcl.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 25, message = "Invalid username length")
	private String username;

	@NotNull
	private String password;

	@Transient
	private String passwordConfirm;

	@ManyToMany
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<Role> roles;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "token_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private RefreshToken token;
	
//	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@ToString.Exclude
//	@EqualsAndHashCode.Exclude
//	@JsonIgnore
//	private Cart cart;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<Order> orders;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<Payment> payments;
}
