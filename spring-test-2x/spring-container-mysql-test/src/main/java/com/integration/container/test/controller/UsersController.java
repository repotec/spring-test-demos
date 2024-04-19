package com.integration.container.test.controller;

import java.util.List;

import com.integration.container.test.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.integration.container.test.dto.UserDto;
import com.integration.container.test.dto.UserRegisterDto;
import com.integration.container.test.service.UserService;

import javax.validation.Valid;

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
