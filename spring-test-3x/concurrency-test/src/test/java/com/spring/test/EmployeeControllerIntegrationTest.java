package com.spring.test;

import com.spring.test.model.Employee;
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
public class EmployeeControllerIntegrationTest {
		
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Test
	@Order(1)
	void test_jsonCreateNewUser_whenValidUserDetailProvided_returnCratedUserDetails() throws Exception {
		JSONObject json = new JSONObject();
		json.put("employeeId", 1);
		json.put("firstName", "operator");
		json.put("lastName", "operator");
		json.put("salary", 1000);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
		
		ParameterizedTypeReference<Employee> responseType = new ParameterizedTypeReference<Employee>() {};
		
		ResponseEntity<Employee> response = testRestTemplate.exchange("/employees", HttpMethod.PUT, request, responseType);
		Employee body = response.getBody();
		
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		Assertions.assertEquals(json.get("employeeId"), body.getEmployeeId());
		Assertions.assertEquals(json.get("firstName"), body.getFirstName());
		Assertions.assertEquals(json.get("lastName"), body.getLastName());
		Assertions.assertEquals(json.get("salary"), body.getSalary());
	}
}

