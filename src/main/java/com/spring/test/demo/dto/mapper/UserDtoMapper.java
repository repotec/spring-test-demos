package com.spring.test.demo.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.spring.test.demo.dto.UserDto;
import com.spring.test.demo.model.User;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "enabled", source = "enabled")
	UserDto entityToDto(User user);
    
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "enabled", source = "enabled")
    User dtoToEntity(UserDto userDto);
}
