package com.spring.test.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.spring.test.demo.dto.UserDto;
import com.spring.test.demo.dto.UserRegisterDto;

public interface UserServiceImpl {
	UserDto createUser(UserRegisterDto userRegisterDto);
	List<UserDto> getAllUsers();
	UserDto findUserByUsername(String username);
}
