package com.example.controller;

import static com.example.constants.UserConstants.PASSWORD_ADMIN;
import static com.example.constants.UserConstants.USERNAME_ADMIN;
import static com.example.constants.UserConstants.NEW_USERNAME;
import static com.example.constants.UserConstants.PAGE_SIZE;
import static com.example.constants.UserConstants.PASSWORD;
import static com.example.constants.UserConstants.NEW_EMAIL;
import static com.example.constants.UserConstants.NEW_PHONE_NO;
import static com.example.constants.UserConstants.NEW_STREET;
import static com.example.constants.UserConstants.NEW_NUMBER;
import static com.example.constants.UserConstants.NEW_ZIP_CODE;
import static com.example.constants.UserConstants.NEW_CITY;
import static com.example.constants.UserConstants.USERNAME;
import static com.example.constants.UserConstants.ID_USER;
import static com.example.constants.UserConstants.ID_NOT_FOUND;
import static com.example.constants.UserConstants.EMAIL;
import static com.example.constants.UserConstants.PHONE_NO;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.TestUtils;
import com.example.dto.AddressDTO;
import com.example.dto.LoginDTO;
import com.example.dto.UserDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class TenantControllerTest {
	private String accessToken;
	private String accessTokenTenant;

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

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/login",
				new LoginDTO(USERNAME_ADMIN, PASSWORD_ADMIN), String.class);
		accessToken = responseEntity.getBody();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddTenant() throws Exception {
		AddressDTO addressDTO = new AddressDTO(NEW_STREET, NEW_NUMBER, NEW_ZIP_CODE, NEW_CITY);
		UserDTO userDTO = new UserDTO(NEW_USERNAME, NEW_EMAIL, addressDTO, NEW_PHONE_NO);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(post("/api/aparments/1/tenants").header("X-Auth-Token", accessToken).contentType(contentType)
				.content(json)).andExpect(status().isCreated()).andExpect(jsonPath("$.username").value(NEW_USERNAME))
				.andExpect(jsonPath("$.email").value(NEW_EMAIL))
				.andExpect(jsonPath("$.address.street").value(NEW_STREET))
				.andExpect(jsonPath("$.address.number").value(NEW_NUMBER))
				.andExpect(jsonPath("$.address.zipCode").value(NEW_ZIP_CODE))
				.andExpect(jsonPath("$.address.city").value(NEW_CITY))
				.andExpect(jsonPath("$.phoneNo").value(NEW_PHONE_NO));

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddTenantInApartment() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(USERNAME);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(post("/api/aparments/1/tenants").header("X-Auth-Token", accessToken).contentType(contentType)
				.content(json)).andExpect(status().isCreated()).andExpect(jsonPath("$.username").value(USERNAME))
				.andExpect(jsonPath("$.email").value(EMAIL)).andExpect(jsonPath("$.phoneNo").value(PHONE_NO));

	}

	@Test
	public void testAddTenantUnauthorized() throws Exception {
		AddressDTO addressDTO = new AddressDTO(NEW_STREET, NEW_NUMBER, NEW_ZIP_CODE, NEW_CITY);
		UserDTO userDTO = new UserDTO(NEW_USERNAME, NEW_EMAIL, addressDTO, NEW_PHONE_NO);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(post("/aparments/1/tenants").contentType(contentType).content(json))
				.andExpect(status().is(403));

	}

	@Test
	public void testAddTenantBadRequest() throws Exception {
		AddressDTO addressDTO = new AddressDTO(NEW_STREET, NEW_NUMBER, NEW_ZIP_CODE, NEW_CITY);
		UserDTO userDTO = new UserDTO(NEW_USERNAME, NEW_EMAIL, addressDTO, NEW_PHONE_NO);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(post("/api/aparments/10/tenants").header("X-Auth-Token", accessToken).contentType(contentType)
				.content(json)).andExpect(status().isBadRequest());
	}

	@Test
	public void testGetTenants() throws Exception {
		mockMvc.perform(get("/api/tenants?page=0&size=" + PAGE_SIZE).header("X-Auth-Token", accessToken))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.[*].username").value(hasItem(USERNAME)));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteTenant() throws Exception {
		mockMvc.perform(
				delete("/api/apartment/" + 1 + "/tenants/" + ID_USER.intValue()).header("X-Auth-Token", accessToken))
				.andExpect(status().isOk());

	}

	@Test
	public void testDeleteTenantNotFound() throws Exception {
		mockMvc.perform(delete("/api/apartment/" + 1 + "/tenants/" + ID_NOT_FOUND).header("X-Auth-Token", accessToken))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testDeleteTenantBadRequest() throws Exception {
		mockMvc.perform(delete("/api/apartment/" + 10000L + "/tenants/" + ID_USER.intValue()).header("X-Auth-Token",
				accessToken)).andExpect(status().isBadRequest());

	}

	@Before
	public void loginTenant() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/login",
				new LoginDTO(USERNAME, PASSWORD), String.class);
		accessTokenTenant = responseEntity.getBody();
	}

	@Test
	public void testGetResponsiblePerson() throws Exception {
		mockMvc.perform(
				get("/api/responsibleGlitches?page=0&size=" + PAGE_SIZE).header("X-Auth-Token", accessTokenTenant))
				.andExpect(status().isOk());
	}
}