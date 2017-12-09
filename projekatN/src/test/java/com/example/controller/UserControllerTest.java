package com.example.controller;

import static com.example.constants.UserConstants.PAGE_SIZE;
import static com.example.constants.UserConstants.PASSWORD_ADMIN;
import static com.example.constants.UserConstants.USERNAME_ADMIN;
import static com.example.constants.UserConstants.ID_ADMIN;
import static com.example.constants.UserConstants.NEW_EMAIL;
import static com.example.constants.UserConstants.NEW_USERNAME;
import static com.example.constants.UserConstants.NEW_PHONE_NO;
import static com.example.constants.UserConstants.NEW_PASSWORD;
import static com.example.constants.UserConstants.NEW_CITY;
import static com.example.constants.UserConstants.NEW_NUMBER;
import static com.example.constants.UserConstants.NEW_STREET;
import static com.example.constants.UserConstants.NEW_ZIP_CODE;
import static com.example.constants.ApartmentConstants.ID_APARTMENT;
import static com.example.constants.UserConstants.ID_NOT_FOUND;
import static com.example.constants.UserConstants.ID_USER;
import static com.example.constants.ApartmentConstants.ID_APARTMENT_NOT_FOUND;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
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
import com.example.dto.RegisterDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserPasswordDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class UserControllerTest {

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
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain).build();
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/login",
				new LoginDTO(USERNAME_ADMIN, PASSWORD_ADMIN), String.class);
		accessToken = responseEntity.getBody();
	}

	@Test
	public void testLogin() throws Exception {
		LoginDTO loginDTO = new LoginDTO(USERNAME_ADMIN, PASSWORD_ADMIN);
		String json = TestUtils.convertObjectToJson(loginDTO);

		mockMvc.perform(post("/api/login").contentType(contentType).content(json)).andExpect(status().isOk());

	}
	
	@Test
	public void testLoginBadRequest() throws Exception {
		LoginDTO loginDTO = new LoginDTO(NEW_USERNAME, PASSWORD_ADMIN);
		String json = TestUtils.convertObjectToJson(loginDTO);

		mockMvc.perform(post("/api/login").contentType(contentType).content(json)).andExpect(status().isBadRequest());

	}


	@Test
	public void testGetUsers() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", accessToken);
		
		mockMvc.perform(get("/api/users?page=0&size=" + PAGE_SIZE).headers(headers))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(PAGE_SIZE)));
	}
	
	@Test
	public void testGetUser() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", accessToken);
		
		mockMvc.perform(get("/api/users/"+ID_ADMIN.intValue()).headers(headers))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(ID_ADMIN.intValue()))
		.andExpect(jsonPath("$.username").value(USERNAME_ADMIN));
	}
	
	@Test
	public void testGetUserNotFound() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", accessToken);
		
		mockMvc.perform(get("/api/users/"+ID_NOT_FOUND).headers(headers))
		.andExpect(status().isNotFound());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testRegister() throws Exception{
		AddressDTO addressDTO = new AddressDTO(NEW_STREET, NEW_NUMBER, NEW_ZIP_CODE, NEW_CITY);
		RegisterDTO registerDTO = new RegisterDTO(NEW_USERNAME, NEW_PASSWORD, NEW_PASSWORD, NEW_EMAIL, addressDTO, NEW_PHONE_NO);
		registerDTO.setApartmentId(ID_APARTMENT);
		String json = TestUtils.convertObjectToJson(registerDTO);
		
		mockMvc.perform(post("/api/register").contentType(contentType).content(json))
		.andExpect(status().isCreated());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testRegisterBadRequest() throws Exception{
		AddressDTO addressDTO = new AddressDTO(NEW_STREET, NEW_NUMBER, NEW_ZIP_CODE, NEW_CITY);
		RegisterDTO registerDTO = new RegisterDTO(NEW_USERNAME, PASSWORD_ADMIN, NEW_PASSWORD, NEW_EMAIL, addressDTO, NEW_PHONE_NO);
		registerDTO.setApartmentId(ID_APARTMENT);
		String json = TestUtils.convertObjectToJson(registerDTO);
		
		mockMvc.perform(post("/api/register").contentType(contentType).content(json))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testRegisterBadApartmentId() throws Exception{
		AddressDTO addressDTO = new AddressDTO(NEW_STREET, NEW_NUMBER, NEW_ZIP_CODE, NEW_CITY);
		RegisterDTO registerDTO = new RegisterDTO(NEW_USERNAME, NEW_PASSWORD, NEW_PASSWORD, NEW_EMAIL, addressDTO, NEW_PHONE_NO);
		registerDTO.setApartmentId(ID_APARTMENT_NOT_FOUND);
		String json = TestUtils.convertObjectToJson(registerDTO);
		
		mockMvc.perform(post("/api/register").contentType(contentType).content(json))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateTenant() throws Exception{
		AddressDTO addressDTO = new AddressDTO(NEW_STREET, NEW_NUMBER, NEW_ZIP_CODE, NEW_CITY);
		UserDTO userDTO = new UserDTO(ID_USER,NEW_USERNAME, NEW_EMAIL, addressDTO, NEW_PHONE_NO);
		String json = TestUtils.convertObjectToJson(userDTO);
		
		mockMvc.perform(put("/api/users")
				.header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.email").value(NEW_EMAIL))
			.andExpect(jsonPath("$.address.street").value(NEW_STREET))
			.andExpect(jsonPath("$.address.number").value(NEW_NUMBER))
			.andExpect(jsonPath("$.address.zipCode").value(NEW_ZIP_CODE))
			.andExpect(jsonPath("$.address.city").value(NEW_CITY))
			.andExpect(jsonPath("$.phoneNo").value(NEW_PHONE_NO));
	}
	
	@Test
	public void testUpdateTenantBadRequest() throws Exception{
		AddressDTO addressDTO = new AddressDTO(NEW_STREET, NEW_NUMBER, NEW_ZIP_CODE, NEW_CITY);
		UserDTO userDTO = new UserDTO(ID_NOT_FOUND,NEW_USERNAME, NEW_EMAIL, addressDTO, NEW_PHONE_NO);
		String json = TestUtils.convertObjectToJson(userDTO);
		
		mockMvc.perform(put("/api/users")
				.header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testChangePassword() throws Exception{
		UserPasswordDTO userPasswordDTO = new UserPasswordDTO(PASSWORD_ADMIN, NEW_PASSWORD, NEW_PASSWORD);
		
		String json = TestUtils.convertObjectToJson(userPasswordDTO);
		
		mockMvc.perform(put("/api/users/password").header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testChangePasswordBadRequest() throws Exception{
		UserPasswordDTO userPasswordDTO = new UserPasswordDTO(PASSWORD_ADMIN, PASSWORD_ADMIN, NEW_PASSWORD);
		
		String json = TestUtils.convertObjectToJson(userPasswordDTO);
		
		mockMvc.perform(put("/api/users/password").header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isBadRequest());
	}
	

}
