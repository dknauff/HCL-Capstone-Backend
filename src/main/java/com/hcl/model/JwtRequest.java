package com.hcl.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -691462575823044369L;
	private String username;
	private String password;
}
