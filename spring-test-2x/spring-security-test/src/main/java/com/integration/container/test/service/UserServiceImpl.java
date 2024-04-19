package com.integration.container.test.service;

import java.util.List;

import com.integration.container.test.dto.UserDto;
import com.integration.container.test.dto.UserRegisterDto;

public interface UserServiceImpl {
	UserDto createUser(UserRegisterDto userRegisterDto);
	List<UserDto> getAllUsers();
	UserDto findUserByUsername(String username);
}
