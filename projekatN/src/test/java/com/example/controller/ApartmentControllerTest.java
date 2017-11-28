package com.example.controller;

import static com.example.constants.UserConstants.PASSWORD_ADMIN;
import static com.example.constants.UserConstants.USERNAME_ADMIN;
import static com.example.constants.ApartmentConstants.ID_APARTMENT;
import static com.example.constants.ApartmentConstants.ID_BUILDING;
import static com.example.constants.ApartmentConstants.PAGE_SIZE;
import static com.example.constants.ApartmentConstants.DESCRIPTION;
import static com.example.constants.ApartmentConstants.NUMBER;
import static com.example.constants.ApartmentConstants.NEW_NUMBER;
import static com.example.constants.ApartmentConstants.NEW_DESCRIPTION;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.example.dto.ApartmentDTO;
import com.example.dto.LoginDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ApartmentControllerTest {
	
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
	public void testGetApartments() throws Exception{
		mockMvc.perform(get("/buildings/" + ID_BUILDING + 
				"/apartments?page=0&size="+PAGE_SIZE )
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",  hasSize(PAGE_SIZE)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(ID_APARTMENT.intValue())))
		.andExpect(jsonPath("$.[*].description").value(hasItem(DESCRIPTION)))
		.andExpect(jsonPath("$.[*].number").value(hasItem(NUMBER)));
		
	}
	
	@Test
	public void testGetApartment() throws Exception{
		mockMvc.perform(get("/apartments/"+ID_APARTMENT.intValue()).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.description").value(DESCRIPTION))
		.andExpect(jsonPath("$.number").value(NUMBER));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddApartment() throws Exception{
		ApartmentDTO apartmentDTO = new ApartmentDTO(NEW_NUMBER, NEW_DESCRIPTION);
		
		String json = TestUtils.convertObjectToJson(apartmentDTO);
		
		mockMvc.perform(post("/buildings/" + ID_BUILDING + "/apartments")
							.header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.number").value(NEW_NUMBER))
		.andExpect(jsonPath("$.description").value(NEW_DESCRIPTION));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateApartment() throws Exception{
		ApartmentDTO apartmentDTO = new ApartmentDTO(ID_APARTMENT,NEW_NUMBER, NEW_DESCRIPTION);
		
		String json = TestUtils.convertObjectToJson(apartmentDTO);
		
		mockMvc.perform(put("/apartments").header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.number").value(NEW_NUMBER))
		.andExpect(jsonPath("$.description").value(NEW_DESCRIPTION));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteApartment() throws Exception{
		mockMvc.perform(delete("/apartments/" + ID_APARTMENT).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
	}
	
}
