package com.spring.test.demo.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.spring.test.demo.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest  {
	
	@Autowired
	TestEntityManager testEntityManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	void testFindByusername_whenCorrectUsernameGiven_retuenUserEntity() {
		User user = new User();
		user.setUsername("guest");
		user.setPassword("guest");
		user.setFirstName("king");
		user.setLastName("lee");
		user.setEmail("guest@email.com");
		user.setEnabled("1");
		
		testEntityManager.persistAndFlush(user);
		
		Optional<User> foundedUser = userRepository.findByUsername("guest");
		Assertions.assertNotNull(foundedUser);
	}
}
