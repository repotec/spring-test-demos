package com.spring.test.demo.controller.mock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.test.demo.controller.UsersController;
import com.spring.test.demo.dto.UserDto;
import com.spring.test.demo.dto.UserRegisterDto;
import com.spring.test.demo.response.SuccessResponse;
import com.spring.test.demo.service.UserService;

@WebMvcTest(controllers = UsersController.class, 
		    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	UserService userService;
	
	UserRegisterDto userRegisterDto;
	
	@BeforeEach
	void setupUserRegisterDto(){
		userRegisterDto = UserRegisterDto.builder().username("operator")
												   .password("operator")
												   .repassword("operator")
												   .firstName("ahmed")
												   .lastName("mohammed")
												   .email("test-isvalid-email")
												   .build();
	}
	
	@Test
	@Order(1)
	void testCreateNewUser_whenValidUserDetailProvided_returnCratedUserDetails() throws Exception {	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
															  .contentType(MediaType.APPLICATION_JSON)
															  .accept(MediaType.APPLICATION_JSON)
															  .content(new ObjectMapper().writeValueAsString(userRegisterDto));
		
		UserDto userDto = new ModelMapper().map(userRegisterDto, UserDto.class);
		userDto.setUserId(2);
		userDto.setEnabled("1");
		
		Mockito.when(userService.createUser(Mockito.any(UserRegisterDto.class))).thenReturn(userDto);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String responseAsString = result.getResponse().getContentAsString();
		SuccessResponse<UserDto> response = new ObjectMapper().readValue(responseAsString, new TypeReference<SuccessResponse<UserDto>>() {});
		UserDto createdUser = response.getData();
		
		Assertions.assertNotNull(createdUser);
		Assertions.assertEquals(createdUser.getFirstName(), userRegisterDto.getFirstName());
		Assertions.assertEquals(createdUser.getLastName(), userRegisterDto.getLastName());
		Assertions.assertEquals(createdUser.getEmail(), userRegisterDto.getEmail());
	}
	
	
	@Test
	@Order(2)
	void testCreateNewUser_whenProvideNotValidEmail_return400StatusCode() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
															  .contentType(MediaType.APPLICATION_JSON)
															  .accept(MediaType.APPLICATION_JSON)
															  .content(new ObjectMapper().writeValueAsString(userRegisterDto));
		
		UserDto userDto = new ModelMapper().map(userRegisterDto, UserDto.class);
		userDto.setUserId(2);
		userDto.setEnabled("1");
		
		Mockito.when(userService.createUser(Mockito.any(UserRegisterDto.class))).thenReturn(userDto);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		Assertions.assertEquals(400, result.getResponse().getStatus());
	}
}

