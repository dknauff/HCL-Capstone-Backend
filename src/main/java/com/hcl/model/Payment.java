package com.hcl.model;

import java.util.Set;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "PaymentInfo")
public class Payment {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentMethodId;   
    private String paymentMethod;    
    private Integer cardNumber;
    private String firstName;    
    private String lastName;
    private Integer cvv;
    private String ExpDate;
    
    

	@OneToOne(mappedBy="paymentMethodId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)	
	
//	@ToString.Exclude
//	@EqualsAndHashCode.Exclude
    private Set<User> users;
}
