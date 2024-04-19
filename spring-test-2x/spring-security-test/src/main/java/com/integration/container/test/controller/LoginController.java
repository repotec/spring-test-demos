package com.integration.container.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<String> login()  {
		return new ResponseEntity<>("authenticated", HttpStatus.OK);
	}
}
