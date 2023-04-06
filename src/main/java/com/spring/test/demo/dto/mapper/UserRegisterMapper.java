package com.spring.test.demo.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.spring.test.demo.dto.UserDto;
import com.spring.test.demo.dto.UserRegisterDto;
import com.spring.test.demo.model.User;

@Mapper(componentModel = "spring")
public interface UserRegisterMapper {
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
	UserRegisterDto entityToDto(User user);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    User dtoToEntity(UserRegisterDto userRegisterDto);
    
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    UserDto userRegisterDtoToUserDto(UserRegisterDto userRegisterDto);
}
