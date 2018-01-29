package com.example.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
@TestPropertySource(locations="classpath:test.properties")
public class CompanyControllerTest {
	
	private String accessToken;
	
	public static final String NEW_USERNAME_COMPANY = "EPS";
	public static final String NEW_EMAIL_COMPANY = "eps@eps.rs";
	public static final String NEW_PHONE_NO_COMPANY = "123456";
	public static final String NEW_STREET_COMPANY = "Address";
	public static final String NEW_NUMBER_COMPANY = "18";
	public static final int NEW_ZIP_CODE_COMPANY = 21000;
	public static final String NEW_CITY_COMPANY = "Novi Sad";
	public static final int PAGE_SIZE = 4;
	
	public static final Long ID_COMPANY = 3L;

	public static final Long ID_NOT_FOUND = 10L;

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
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/login",
				new LoginDTO("admin", "admin"), String.class);
		accessToken = responseEntity.getBody();
	}
	

	@Test
	@Transactional
    @Rollback(true)
	public void testAddCompany() throws Exception{
		AddressDTO addressDTO = new AddressDTO(NEW_STREET_COMPANY, NEW_NUMBER_COMPANY, NEW_ZIP_CODE_COMPANY, NEW_CITY_COMPANY);
		UserDTO userDTO = new UserDTO(NEW_USERNAME_COMPANY, NEW_EMAIL_COMPANY, addressDTO, NEW_PHONE_NO_COMPANY);
		
		String json = TestUtils.convertObjectToJson(userDTO);
		
		mockMvc.perform(post("/api/companies").header("X-Auth-Token", accessToken)
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
		
		mockMvc.perform(get("/api/companies?page=0&size="+PAGE_SIZE).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(2)));		
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testDeleteCompany() throws Exception{
		mockMvc.perform(delete("/api/companies/" + ID_COMPANY.intValue()).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testDeleteCompanyNotFound() throws Exception{
		mockMvc.perform(delete("/api/companies/" + ID_NOT_FOUND.intValue()).header("X-Auth-Token", accessToken))
		.andExpect(status().isNotFound());
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testCompanyCount() throws Exception{
		mockMvc.perform(get("/api/companies/count").header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testCompanyActiveGliches() throws Exception{
		mockMvc.perform(get("/api/companies/activeGlitches").header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testCompanyPendingGliches() throws Exception{
		mockMvc.perform(get("/api/companies/pendingGlitches").header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
	}
}
