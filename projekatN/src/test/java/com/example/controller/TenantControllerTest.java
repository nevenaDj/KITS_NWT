package com.example.controller;

import static com.example.constants.UserConstants.PASSWORD_ADMIN;
import static com.example.constants.UserConstants.USERNAME_ADMIN;
import static com.example.constants.UserConstants.NEW_USERNAME;
import static com.example.constants.UserConstants.NEW_EMAIL;
import static com.example.constants.UserConstants.NEW_PHONE_NO;
import static com.example.constants.UserConstants.NEW_ADDRESS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.TestUtils;
import com.example.dto.LoginDTO;
import com.example.dto.UserDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TenantControllerTest {
	private String accessToken;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	FilterChainProxy springSecurityFilterChain;

	private MockMvc mockMvc;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@PostConstruct
	public void setup() {
		RestAssured.useRelaxedHTTPSValidation();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain)
				.build();
	}

	@Before
	public void login() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/login",
				new LoginDTO(USERNAME_ADMIN, PASSWORD_ADMIN), String.class);
		accessToken = responseEntity.getBody();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddTenant() throws Exception {
		UserDTO userDTO = new UserDTO(NEW_USERNAME, NEW_EMAIL, NEW_ADDRESS, NEW_PHONE_NO);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(
				post("/aparments/1/tenants").header("X-Auth-Token", accessToken)
										   .contentType(contentType)
										   .content(json))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.username").value(NEW_USERNAME))
				.andExpect(jsonPath("$.email").value(NEW_EMAIL))
				.andExpect(jsonPath("$.address").value(NEW_ADDRESS))
				.andExpect(jsonPath("$.phoneNo").value(NEW_PHONE_NO));

	}
	
	@Test
	public void testAddTenantUnauthorized() throws Exception {
		UserDTO userDTO = new UserDTO(NEW_USERNAME, NEW_EMAIL, NEW_ADDRESS, NEW_PHONE_NO);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(
				post("/aparments/1/tenants")
										   .contentType(contentType)
										   .content(json))
				.andExpect(status().is(403));

	}
	
	@Before
	public void loginAdmin() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/login",
				new LoginDTO(USERNAME_ADMIN, PASSWORD_ADMIN), String.class);
		accessToken = responseEntity.getBody();
	}
	
	@Test
	public void testAddTenantBadRequest() throws Exception {
		UserDTO userDTO = new UserDTO(NEW_USERNAME, NEW_EMAIL, NEW_ADDRESS, NEW_PHONE_NO);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(
				post("/aparments/10/tenants").header("X-Auth-Token", accessToken)
										   .contentType(contentType)
										   .content(json))
				.andExpect(status().isBadRequest());
	}

}
