package com.example.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import static com.example.constants.UserConstants.PASSWORD_ADMIN;
import static com.example.constants.UserConstants.USERNAME_ADMIN;
import static com.example.constants.BuildingConstatnts.BUILDING_ADDRESS_1;
import static com.example.constants.BuildingConstatnts.BUILDING_ADDRESS_2;
import static com.example.constants.BuildingConstatnts.BUILDING_ID_1;
import static com.example.constants.BuildingConstatnts.BUILDING_ID_2;
import static com.example.constants.BuildingConstatnts.NEW_ADDRESS;
import static com.example.constants.BuildingConstatnts.NEW_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.example.constants.BuildingConstatnts.PAGE_SIZE;

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
import com.example.dto.BuildingDTO;
import com.example.dto.LoginDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class BuildingControllerTest {
	
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
	public void testGetBuildings() throws Exception{
		mockMvc.perform(get("/buildings?page=0&size=" + PAGE_SIZE).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(PAGE_SIZE)))
		.andExpect(jsonPath("$.[*].address").value(hasItem(BUILDING_ADDRESS_1)))
		.andExpect(jsonPath("$.[*].address").value(hasItem(BUILDING_ADDRESS_2)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(BUILDING_ID_1.intValue())))
		.andExpect(jsonPath("$.[*].id").value(hasItem(BUILDING_ID_2.intValue())));
	}
	
	@Test
	public void testGetBuilding() throws Exception{
		mockMvc.perform(get("/buildings/" + BUILDING_ID_1).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(BUILDING_ID_1))
		.andExpect(jsonPath("$.address").value(BUILDING_ADDRESS_1));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddBuilding() throws Exception{
		BuildingDTO buildingDTO = new BuildingDTO(NEW_ADDRESS);
		
		String json = TestUtils.convertObjectToJson(buildingDTO);
		
		mockMvc.perform(post("/buildings").header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(NEW_ID))
		.andExpect(jsonPath("$.address").value(NEW_ADDRESS));
	
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateBuilding() throws Exception{
		BuildingDTO buildingDTO = new BuildingDTO(BUILDING_ID_1, NEW_ADDRESS);
		
		String json = TestUtils.convertObjectToJson(buildingDTO);
		
		mockMvc.perform(put("/buildings").header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.address").value(NEW_ADDRESS));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteBuilding() throws Exception{
		mockMvc.perform(delete("/buildings/"+BUILDING_ID_1).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
	}

}
