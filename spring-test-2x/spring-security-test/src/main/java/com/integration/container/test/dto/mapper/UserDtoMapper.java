package com.integration.container.test.dto.mapper;

import com.integration.container.test.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.integration.container.test.dto.UserDto;

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
