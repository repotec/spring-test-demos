package com.spring.test.demo.controller.unit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
public class UsersControllerUnitTest {
	
	@Autowired
	MockMvc mockMvc;
	
	//spring annotation equivalent of @InjectMock and @Mock in Mockito 
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
												   .email("testmail@test.com")
												   .build();
	}
	
	@Test
	@Order(1)
	void testCreateNewUser_whenValidUserDetailProvided_returnCratedUserDetails() throws Exception {	
		//given
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
															  .contentType(MediaType.APPLICATION_JSON)
															  .accept(MediaType.APPLICATION_JSON)
															  .content(new ObjectMapper().writeValueAsString(userRegisterDto));
		
		UserDto userDto = new ModelMapper().map(userRegisterDto, UserDto.class);
		
		userDto.setUserId(200);
		userDto.setEnabled("1");
		
		//when
		Mockito.when(userService.createUser(Mockito.any(UserRegisterDto.class))).thenReturn(userDto);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String responseAsString = result.getResponse().getContentAsString();
		SuccessResponse<UserDto> response = new ObjectMapper().readValue(responseAsString, new TypeReference<SuccessResponse<UserDto>>() {});
		UserDto createdUser = response.getData();
		
		//then
		Assertions.assertNotNull(createdUser);
		Assertions.assertEquals(createdUser.getFirstName(), userRegisterDto.getFirstName());
		Assertions.assertEquals(createdUser.getLastName(), userRegisterDto.getLastName());
		Assertions.assertEquals(createdUser.getEmail(), userRegisterDto.getEmail());
	}
	
	@Test
	@Order(2)
	void testCreateNewUser_whenValidUserDetailProvided_returnCratedUserDetails2() throws Exception {	
		UserDto userDto = new ModelMapper().map(userRegisterDto, UserDto.class);
		
		userDto.setUserId(2333);
		userDto.setEnabled("1");
		
		Mockito.when(userService.createUser(Mockito.any(UserRegisterDto.class))).thenReturn(userDto);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users")
										   .content(new ObjectMapper().writeValueAsString(userRegisterDto))
										   .accept(MediaType.APPLICATION_JSON)
										   .contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		String responseAsString = result.getResponse().getContentAsString();
		SuccessResponse<UserDto> response = new ObjectMapper().readValue(responseAsString, new TypeReference<SuccessResponse<UserDto>>() {});
		UserDto createdUser = response.getData();
		
		Assertions.assertNotNull(createdUser);
		Assertions.assertEquals(createdUser.getFirstName(), userRegisterDto.getFirstName());
		Assertions.assertEquals(createdUser.getLastName(), userRegisterDto.getLastName());
		Assertions.assertEquals(createdUser.getEmail(), userRegisterDto.getEmail());
	}
	
	
	
	@Test
	@Order(3)
	void testCreateNewUser_whenProvideNotValidEmail_return400StatusCode() throws Exception {
		//stub for users post endpoint
		//given
		userRegisterDto.setEmail("test");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
															  .contentType(MediaType.APPLICATION_JSON)
															  .accept(MediaType.APPLICATION_JSON)
															  .content(new ObjectMapper().writeValueAsString(userRegisterDto));
		
		UserDto userDto = new ModelMapper().map(userRegisterDto, UserDto.class);
		userDto.setUserId(2);
		userDto.setEnabled("1");
		
		//when
		Mockito.when(userService.createUser(Mockito.any(UserRegisterDto.class))).thenReturn(userDto);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		//then
		Assertions.assertEquals(400, result.getResponse().getStatus());
	}
	
	
	@Test
	@Order(4)
	void testCreateNewUser_whenProvideNotValidEmail_return400StatusCode_withoutReturn() throws Exception {
		//stub for users post endpoint
		//given
		userRegisterDto.setEmail("test");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
															  .contentType(MediaType.APPLICATION_JSON)
															  .accept(MediaType.APPLICATION_JSON)
															  .content(new ObjectMapper().writeValueAsString(userRegisterDto));
		
		//when
		Mockito.when(userService.createUser(Mockito.any(UserRegisterDto.class))).thenReturn(null);
		
		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
}

