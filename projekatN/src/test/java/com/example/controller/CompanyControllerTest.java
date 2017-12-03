package com.example.controller;

import static com.example.constants.UserConstants.PASSWORD_ADMIN;
import static com.example.constants.UserConstants.USERNAME_ADMIN;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.example.constants.UserConstants.NEW_USERNAME_COMPANY;
import static com.example.constants.UserConstants.NEW_EMAIL_COMPANY;
import static com.example.constants.UserConstants.NEW_STREET_COMPANY;
import static com.example.constants.UserConstants.NEW_CITY_COMPANY;
import static com.example.constants.UserConstants.NEW_NUMBER_COMPANY;
import static com.example.constants.UserConstants.NEW_ZIP_CODE_COMPANY;
import static com.example.constants.UserConstants.NEW_PHONE_NO_COMPANY;
import static com.example.constants.UserConstants.PAGE_SIZE;
import static com.example.constants.UserConstants.USERNAME_COMPANY;
import static com.example.constants.UserConstants.ID_COMPANY;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

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
import com.example.dto.AddressDTO;
import com.example.dto.LoginDTO;
import com.example.dto.UserDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CompanyControllerTest {
	
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
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
									  .addFilters(springSecurityFilterChain)
									  .build();
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/login",
				new LoginDTO(USERNAME_ADMIN, PASSWORD_ADMIN), String.class);
		accessToken = responseEntity.getBody();
	}
	

	@Test
	@Transactional
    @Rollback(true)
	public void testAddCompany() throws Exception{
		AddressDTO addressDTO = new AddressDTO(NEW_STREET_COMPANY, NEW_NUMBER_COMPANY, NEW_ZIP_CODE_COMPANY, NEW_CITY_COMPANY);
		UserDTO userDTO = new UserDTO(NEW_USERNAME_COMPANY, NEW_EMAIL_COMPANY, addressDTO, NEW_PHONE_NO_COMPANY);
		
		String json = TestUtils.convertObjectToJson(userDTO);
		
		mockMvc.perform(post("/companies").header("X-Auth-Token", accessToken)
										  .contentType(contentType)
										  .content(json))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.username").value(NEW_USERNAME_COMPANY))
		.andExpect(jsonPath("$.email").value(NEW_EMAIL_COMPANY))
		.andExpect(jsonPath("$.address.street").value(NEW_STREET_COMPANY))
		.andExpect(jsonPath("$.address.number").value(NEW_NUMBER_COMPANY))
		.andExpect(jsonPath("$.address.zipCode").value(NEW_ZIP_CODE_COMPANY))
		.andExpect(jsonPath("$.address.city").value(NEW_CITY_COMPANY))
		.andExpect(jsonPath("$.phoneNo").value(NEW_PHONE_NO_COMPANY));		
		
	}
	
	@Test
	public void testGetCompanies() throws Exception{
		
		mockMvc.perform(get("/companies?page=0&size="+PAGE_SIZE).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$.[*].username").value(USERNAME_COMPANY));
		
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testUpdateCompany() throws Exception{
		AddressDTO addressDTO = new AddressDTO(NEW_STREET_COMPANY, NEW_NUMBER_COMPANY, NEW_ZIP_CODE_COMPANY, NEW_CITY_COMPANY);
		UserDTO userDTO = new UserDTO(ID_COMPANY,NEW_USERNAME_COMPANY, NEW_EMAIL_COMPANY, addressDTO, NEW_PHONE_NO_COMPANY);
		
		String json = TestUtils.convertObjectToJson(userDTO);
		
		mockMvc.perform(put("/companies").header("X-Auth-Token", accessToken)
										 .contentType(contentType)
										 .content(json))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.email").value(NEW_EMAIL_COMPANY))
		.andExpect(jsonPath("$.address.street").value(NEW_STREET_COMPANY))
		.andExpect(jsonPath("$.address.number").value(NEW_NUMBER_COMPANY))
		.andExpect(jsonPath("$.address.zipCode").value(NEW_ZIP_CODE_COMPANY))
		.andExpect(jsonPath("$.address.city").value(NEW_CITY_COMPANY))
		.andExpect(jsonPath("$.phoneNo").value(NEW_PHONE_NO_COMPANY));
		
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testDeleteCompany() throws Exception{
		mockMvc.perform(delete("/companies/" + ID_COMPANY.intValue()).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
	}


}
