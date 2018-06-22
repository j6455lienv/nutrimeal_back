package com.example.nutrimeal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@RequestMapping( value = "/test", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> testControler() {
		String test ="test";
		return new ResponseEntity<>(test,HttpStatus.OK);
	}
}
