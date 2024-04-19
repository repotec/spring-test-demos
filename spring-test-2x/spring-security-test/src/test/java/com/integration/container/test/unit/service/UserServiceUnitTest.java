package com.integration.container.test.unit.service;

import com.integration.container.test.dto.UserDto;
import com.integration.container.test.dto.UserRegisterDto;
import com.integration.container.test.dto.mapper.UserDtoMapper;
import com.integration.container.test.dto.mapper.UserRegisterMapper;
import com.integration.container.test.model.User;
import com.integration.container.test.repository.UserRepository;
import com.integration.container.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

	@InjectMocks
    UserService userService;
	
	@Mock
    UserRepository userRepository;
	
	//we don't need to mock the behavior of mappers, we need the use the real object 
	//@SpyBean
	@Spy
	private UserRegisterMapper userRegisterMapper = Mappers.getMapper(UserRegisterMapper.class);
	
	@Spy
	private UserDtoMapper userDtoMapper = Mappers.getMapper(UserDtoMapper.class);
	
	@Test
	void test() {
		final var user = User.builder().username("operator")
									   .password("operator")
									   .firstName("Lee")
									   .lastName("Kevin")
									   .email("test@email.com")
									   .build();
		
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
		
		final var userRegisterDto = UserRegisterDto.builder().username("operator")
														     .password("operator")
														     .repassword("operator")
														     .firstName("Lee")
														     .lastName("Kevin")
													   	     .email("test@email.com")
														     .build();
		 
		UserDto createdUser = userService.createUser(userRegisterDto);
		
		Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
	}
}
