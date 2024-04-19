package com.integration.container.test.dto.mapper;

import com.integration.container.test.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.integration.container.test.dto.UserDto;
import com.integration.container.test.dto.UserRegisterDto;

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
