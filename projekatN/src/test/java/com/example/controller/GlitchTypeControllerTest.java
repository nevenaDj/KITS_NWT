package com.example.controller;

import static com.example.constants.GlitchConstants.NEW_TYPE;
import static com.example.constants.UserConstants.PASSWORD_ADMIN;
import static com.example.constants.UserConstants.USERNAME_ADMIN;
import static com.example.constants.GlitchConstants.ID_GLITCH_TYPE;
import static com.example.constants.GlitchConstants.ID_NOT_FOUND;
import static com.example.constants.GlitchConstants.TYPE;
import static org.hamcrest.CoreMatchers.hasItem;
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
import com.example.dto.GlitchTypeDTO;
import com.example.dto.LoginDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class GlitchTypeControllerTest {
	private String accessToken;
	private String accessTokenPresident;
	

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
		ResponseEntity<String> responseEntity2 = restTemplate.postForEntity("/api/login",
				new LoginDTO("president", "user"), String.class);
		accessTokenPresident = responseEntity2.getBody();
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddGlitchType() throws Exception{
		GlitchTypeDTO glitchTypeDTO = new GlitchTypeDTO();
		glitchTypeDTO.setType(NEW_TYPE);
		
		String json = TestUtils.convertObjectToJson(glitchTypeDTO);
		
		mockMvc.perform(post("/api/glitchTypes").header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.type").value(NEW_TYPE));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteGlitchType() throws Exception{
		mockMvc.perform(delete("/api/glitchTypes/" + ID_GLITCH_TYPE).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
	}

	@Test
	public void testDeleteGlitchTypeNotFound() throws Exception{
		mockMvc.perform(delete("/api/glitchTypes/" + ID_NOT_FOUND).header("X-Auth-Token", accessToken))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetGliychType() throws Exception{
		mockMvc.perform(get("/api/glitchTypes/" + ID_GLITCH_TYPE).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.type").value(TYPE));
	}
	
	@Test
	public void testGetGliychTypeNotFound() throws Exception{
		mockMvc.perform(get("/api/glitchTypes/" + ID_NOT_FOUND).header("X-Auth-Token", accessToken))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void testFindGlitchTypeByName() throws Exception{
		mockMvc.perform(get("/api/glitchTypes?name=" + TYPE).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(ID_GLITCH_TYPE));
	}
	
	@Test
	public void testFindGlitchTypeByNameNotFound() throws Exception{
		mockMvc.perform(get("/api/glitchTypes?name=" + NEW_TYPE).header("X-Auth-Token", accessToken))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetAllGlitchTypes() throws Exception{
		mockMvc.perform(get("/api/glitchTypes").header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[*].type").value(hasItem(TYPE)));
	}
	
	@Test
	public void testGetAllGlitchType() throws Exception{
		mockMvc.perform(get("/api/glitchTypes/"+1L+"/companies").header("X-Auth-Token", accessTokenPresident))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testGetAllGlitchTypeNF() throws Exception{
		mockMvc.perform(get("/api/glitchTypes/"+10000L+"/companies").header("X-Auth-Token", accessTokenPresident))
		.andExpect(status().isNotFound());
	}
}
