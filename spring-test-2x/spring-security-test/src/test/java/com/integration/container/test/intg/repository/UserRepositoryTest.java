package com.integration.container.test.intg.repository;

import java.util.Optional;

import com.integration.container.test.model.User;
import com.integration.container.test.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

@DataJpaTest
@TestPropertySource(properties = {
		"spring.flyway.enables=false",
		"spring.jpa.hibernate.auto-dll=create-drop"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest  {
	
	@Autowired
	TestEntityManager testEntityManager;
	
	@Autowired
    UserRepository userRepository;

	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
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
