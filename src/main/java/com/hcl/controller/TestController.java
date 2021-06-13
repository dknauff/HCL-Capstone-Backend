package com.hcl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/testing")
public class TestController {
	
	@GetMapping(path="/authTest")
	public ResponseEntity<?> testJWTAuth(){
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(path="/authFindTest")
	public ResponseEntity<?> testJWTAndFind(@RequestBody String string){
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
