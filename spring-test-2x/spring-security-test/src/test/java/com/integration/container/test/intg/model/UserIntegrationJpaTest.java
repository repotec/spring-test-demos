package com.integration.container.test.intg.model;

import com.integration.container.test.model.User;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(locations = "/application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserIntegrationJpaTest {
	
	@Autowired
	TestEntityManager testEntityManager;
	
	@Test
	@Order(1)
	void testCreateNewUser_whenValidUserDetailProvided_returnCratedUserDetails() {	
		User user = new User();
		user.setUsername("guest");
		user.setPassword("guest");
		user.setFirstName("king");
		user.setLastName("lee");
		user.setEmail("guest@email.com");
		user.setEnabled("1");
		
		User newUser = testEntityManager.persistAndFlush(user);
		
		MatcherAssert.assertThat(newUser.getUserId(), CoreMatchers.equalTo(1));
		MatcherAssert.assertThat(newUser.getUsername(), CoreMatchers.equalTo(user.getUsername()));
		MatcherAssert.assertThat(newUser.getPassword(), CoreMatchers.equalTo(user.getPassword()));
		MatcherAssert.assertThat(newUser.getFirstName(), CoreMatchers.equalTo(user.getFirstName()));
		MatcherAssert.assertThat(newUser.getLastName(), CoreMatchers.equalTo(user.getLastName()));
		MatcherAssert.assertThat(newUser.getEmail(), CoreMatchers.equalTo(user.getEmail()));
		MatcherAssert.assertThat(newUser.getEnabled(), CoreMatchers.equalTo("1"));
	}
	
	@Test
	@Order(2)
	void testCreateNewUser_whenFirstNameNotProvided_ShouldThrowPersistenceException() {	
		User user = new User();
		user.setUsername("guest");
		user.setPassword("guest");
		user.setFirstName("king");
		//user.setLastName("lee");
		user.setEmail("guest@email.com");
		user.setEnabled("1");
		
		Assertions.assertThrowsExactly(javax.persistence.PersistenceException.class,
				()-> testEntityManager.persistAndFlush(user));
	}
	
	@Test
	@Order(2)
	void testCreateNewUser_whenNotValidEmailProvided_ShouldThrowConstraintViolationException() {	
		User user = new User();
		user.setUsername("guest");
		user.setPassword("guest");
		user.setFirstName("king");
		user.setLastName("lee");
		user.setEmail("guest");
		user.setEnabled("1");
		
		Assertions.assertThrowsExactly(javax.validation.ConstraintViolationException.class,
				()-> testEntityManager.persistAndFlush(user));
	}
}
