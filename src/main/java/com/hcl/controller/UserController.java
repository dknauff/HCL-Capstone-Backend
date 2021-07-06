package com.hcl.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.auth.GetAuth;
import com.hcl.model.JwtRequest;
import com.hcl.model.JwtResponse;
import com.hcl.model.RefreshToken;
import com.hcl.model.User;
import com.hcl.security.JwtTokenUtil;
import com.hcl.service.UserService;
import com.hcl.validator.UserValidator;

@CrossOrigin(value = "https://capstone-front-end-react.herokuapp.com/", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping(path = "/users")
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GetAuth authService;
	
	@Autowired
	private JwtTokenUtil tokenUtil;
	
	@Autowired
	private UserValidator userValidator;

	@PostMapping(path = "/auth")
	// LOGIN FUNCTION, USING JWT
	public ResponseEntity<?> authToken(@RequestBody JwtRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (DisabledException e) {
			logger.error("The user is disabled.");
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			logger.error("The credentials you entered are not valid.");
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		final UserDetails user = userDetailsService.loadUserByUsername(authRequest.getUsername());
		logger.info("JWT token has been generated.");
		// Returns access token
		// TO - DO
		// GENERATE REFRESH TOKEN - SEND BOTH
		return new ResponseEntity<>(new JwtResponse(tokenUtil.generateToken(user)), HttpStatus.OK);
	}

	@PostMapping(path = "/register")
	public ResponseEntity<?> createUser(@RequestBody User user, BindingResult errors) {
		userValidator.validate(user, errors);
		if(errors.hasErrors()) {
			logger.error("There was an error in creating a user.");
			return new ResponseEntity<>(errors.getAllErrors() ,HttpStatus.BAD_REQUEST);
		}
		userService.createUser(user);
		logger.info("A user has been created successfully");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping(path="/role")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getRole(){
		if(!authService.getAuth().isAuthenticated()) 
			return new ResponseEntity<String>("This user is not authenticated!", HttpStatus.BAD_REQUEST);
		
		User user = userService.findByUsername(authService.getAuth().getName());
		if(user == null)
			return new ResponseEntity<String>("This user is not authenticated!", HttpStatus.BAD_REQUEST);
		
		List<String> roles = user.getRoles().stream().map(x -> x.getName()).collect(Collectors.toList());
		return new ResponseEntity<List<String>>(roles, HttpStatus.OK);
	}
	
	@PostMapping(path="/refresh")
	public void resfreshJWT(@RequestBody RefreshToken rjwt) {
		
	}
}
