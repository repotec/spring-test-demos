package com.spring.test.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.test.demo.dto.UserDto;
import com.spring.test.demo.dto.UserRegisterDto;
import com.spring.test.demo.response.SuccessResponse;
import com.spring.test.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

	private UserService usersService;

	@Autowired
	public UsersController(UserService usersService) {
	    this.usersService = usersService;
	}
	
	@PostMapping
	public ResponseEntity<SuccessResponse<UserDto>> createUser(@RequestBody @Valid UserRegisterDto userRegisterDto) throws Exception {
		UserDto userDto = usersService.createUser(userRegisterDto);
		SuccessResponse<UserDto> response = new SuccessResponse<UserDto>(HttpStatus.CREATED.value(), "new user has been created!", userDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

    @GetMapping
    public ResponseEntity<SuccessResponse<List<UserDto>>> getUsers() {
        List<UserDto> users = usersService.getAllUsers();
		SuccessResponse<List<UserDto>> response = new SuccessResponse<List<UserDto>>(HttpStatus.OK.value(), "", users);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
