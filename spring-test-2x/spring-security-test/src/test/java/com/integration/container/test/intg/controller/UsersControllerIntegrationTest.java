package com.integration.container.test.intg.controller;

import com.integration.container.test.dto.UserDto;
import com.integration.container.test.dto.UserRegisterDto;
import com.integration.container.test.response.SuccessResponse;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersControllerIntegrationTest {
		
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Test
	@Order(1)
	void test_jsonCreateNewUser_whenValidUserDetailProvided_returnCratedUserDetails() throws Exception {
		JSONObject json = new JSONObject();
		json.put("username", "operator");
		json.put("password", "operator");
		json.put("repassword", "operator");
		json.put("firstName", "ahmed");
		json.put("lastName", "mohammed");
		json.put("email", "test@mail.com");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
		
		ParameterizedTypeReference<SuccessResponse<UserDto>> responseType = new ParameterizedTypeReference<SuccessResponse<UserDto>>() {};
		
		ResponseEntity<SuccessResponse<UserDto>> response = testRestTemplate.exchange("/users", HttpMethod.POST, request, responseType);
		SuccessResponse<UserDto> body = response.getBody();
		UserDto userDto = body.getData();
		
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		Assertions.assertEquals(json.get("username"), userDto.getUsername());
		Assertions.assertEquals(json.get("firstName"), userDto.getFirstName());
		Assertions.assertEquals(json.get("lastName"), userDto.getLastName());
		Assertions.assertEquals(json.get("email"), userDto.getEmail());
	}
	
	@Test
	@Order(2)
	void testCreateNewUser_whenInValidEmailProvided_return400StatusCode() throws Exception {	
		
		JSONObject json = new JSONObject();
		json.put("username", "operator");
		json.put("password", "operator");
		json.put("repassword", "operator");
		json.put("firstName", "ahmed");
		json.put("lastName", "mohammed");
		json.put("email", "mail.com");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
		
		ParameterizedTypeReference<SuccessResponse<UserDto>> responseType = new ParameterizedTypeReference<SuccessResponse<UserDto>>() {};
		
		ResponseEntity<SuccessResponse<UserDto>> response = testRestTemplate.exchange("/users", HttpMethod.POST, request, responseType);
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@Order(3)
	void testCreateNewUser_whenValidUserDetailProvided_returnCratedUserDetails() throws Exception {	
		UserRegisterDto user = UserRegisterDto.builder().username("guest")
														  .password("guest")
														  .repassword("guest")
														  .firstName("ahmed")
														  .lastName("guest")
														  .email("test@mail.com")
														  .build();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<UserRegisterDto> request = new HttpEntity<>(user, headers);
		
		ParameterizedTypeReference<SuccessResponse<UserDto>> responseType = new ParameterizedTypeReference<SuccessResponse<UserDto>>() {};
		
		ResponseEntity<SuccessResponse<UserDto>> response = testRestTemplate.exchange("/users", HttpMethod.POST, request, responseType);
		SuccessResponse<UserDto> body = response.getBody();
		UserDto userDto = body.getData();
		
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		Assertions.assertEquals(user.getUsername(), userDto.getUsername());
		Assertions.assertEquals(user.getFirstName(), userDto.getFirstName());
		Assertions.assertEquals(user.getLastName(), userDto.getLastName());
		Assertions.assertEquals(user.getEmail(), userDto.getEmail());
	}
}

