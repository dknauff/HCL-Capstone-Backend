package com.hcl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(value = "http://localhost:3000/", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping(path = "/testing")
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.info("Successfully logged some shit bruh");
	}
	
	@GetMapping(path="/authTest")
	public ResponseEntity<?> testJWTAuth(){
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(path="/authFindTest")
	public ResponseEntity<?> testJWTAndFind(@RequestBody String string){
		return new ResponseEntity<>(HttpStatus.OK);
	}
	//logger.info("Successfully logged some shit bruh");
}
