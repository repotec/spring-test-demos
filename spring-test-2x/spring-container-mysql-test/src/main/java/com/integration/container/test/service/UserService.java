package com.integration.container.test.service;

import java.util.List;
import java.util.stream.Collectors;

import com.integration.container.test.exception.PasswordWrongExcpetion;
import com.integration.container.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integration.container.test.dto.UserDto;
import com.integration.container.test.dto.UserRegisterDto;
import com.integration.container.test.dto.mapper.UserDtoMapper;
import com.integration.container.test.dto.mapper.UserRegisterMapper;
import com.integration.container.test.model.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserServiceImpl {
	
	private UserRepository userRepository;
	private UserRegisterMapper userRegisterMapper;
	private UserDtoMapper UserDtoMapper;
	
	@Autowired
	public UserService(UserRepository userRepository, UserDtoMapper UserDtoMapper, UserRegisterMapper userRegisterMapper) {
		this.userRepository = userRepository;
		this.UserDtoMapper = UserDtoMapper;
		this.userRegisterMapper = userRegisterMapper;
	}
	
	@Override
	public UserDto createUser(UserRegisterDto userRegisterDto) {
		 log.info("Passwor:" + userRegisterDto.getPassword() + "|Repassword:" + userRegisterDto.getRepassword());
		 if(userRegisterDto.getPassword().equals(userRegisterDto.getRepassword())) {
			User user = userRegisterMapper.dtoToEntity(userRegisterDto);
			user.setEnabled("1");
			User userCreated = userRepository.save(user);
			System.err.println(userCreated.getPassword());
			return UserDtoMapper.entityToDto(userCreated);
		 }else {
			 throw new PasswordWrongExcpetion("passwords are not matched!");
		 }
	}
	
	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		
		return users.stream().map(UserDtoMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public UserDto findUserByUsername(String username) {
		return UserDtoMapper.entityToDto(userRepository.findByUsername(username).get());
	}
}