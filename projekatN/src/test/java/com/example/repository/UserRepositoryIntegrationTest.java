package com.example.repository;

import static com.example.constants.UserConstants.ID_USER;
import static com.example.constants.UserConstants.USERNAME;
import static com.example.constants.UserConstants.EMAIL;
import static com.example.constants.UserConstants.PHONE_NO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class UserRepositoryIntegrationTest {
	@Autowired
	UserRepository userRepository;

	@Test
	public void testGetUserAuthority() {
		List<String> authority = userRepository.getUserAuthority(ID_USER);

		assertNotNull(authority);
		assertEquals(true, authority.contains("ROLE_USER"));

	}

	@Test
	public void testFindByUsernameAndAuthority() {
		User user = userRepository.findByUsernameAndAuthority(USERNAME, "ROLE_USER");

		assertNotNull(user);
		assertEquals(USERNAME, user.getUsername());
		assertEquals(EMAIL, user.getEmail());
		assertEquals(PHONE_NO, user.getPhoneNo());
	}

	@Test
	public void testFindByUsername() {
		User user = userRepository.findByUsername(USERNAME);

		assertNotNull(user);
		assertEquals(USERNAME, user.getUsername());
		assertEquals(EMAIL, user.getEmail());
		assertEquals(PHONE_NO, user.getPhoneNo());
	}

}
