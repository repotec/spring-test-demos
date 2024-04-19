package com.integration.container.test.intg.controller;

import com.integration.container.test.dto.UserDto;
import com.integration.container.test.response.SuccessResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "/application-intg.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class UsersControllerIntegrationJwtTest {
		
	@Autowired
	TestRestTemplate testRestTemplate;
	
	String jwtToken;
	
	@Test
	@Order(1)
	void testLogin_whenBasicAuthenticationProvided_returnAuthorizationHeader() throws Exception {	
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = testRestTemplate
											.withBasicAuth("admin", "admin")
											.exchange("/login", HttpMethod.POST, request, String.class);
		
		HttpHeaders header = response.getHeaders();
		List<String> headersResponse = header.get("Authorization");

		jwtToken = headersResponse.get(0);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertNotNull(jwtToken, "header should had Authorization value");
	}
	
	@Test
	@Order(2)
	void testGetAllUsers_whenBasicAuthenticationNotProvided_returnUnAuthorizedResponse() throws Exception {	
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		
		ParameterizedTypeReference<SuccessResponse<List<UserDto>>> responseType = new ParameterizedTypeReference<SuccessResponse<List<UserDto>>>() {};

		ResponseEntity<SuccessResponse<List<UserDto>>> response = testRestTemplate.exchange("/users", HttpMethod.GET, request, responseType);

		Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	
	@Test
	@Order(3)
	void testGetAllUsers_whenBasicAuthenticationProvided_returnListOfUsers() throws Exception {	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", jwtToken);
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		
		ParameterizedTypeReference<SuccessResponse<List<UserDto>>> responseType = new ParameterizedTypeReference<SuccessResponse<List<UserDto>>>() {};

		ResponseEntity<SuccessResponse<List<UserDto>>> response = testRestTemplate.exchange("/users", HttpMethod.GET, request, responseType);
		SuccessResponse<List<UserDto>> users = response.getBody();
		//users.getData().stream().forEach(System.out::println);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}

