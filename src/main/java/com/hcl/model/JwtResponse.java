package com.hcl.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class JwtResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7900342567085479512L;
	
	private final @Getter String jwtToken;
	
}
