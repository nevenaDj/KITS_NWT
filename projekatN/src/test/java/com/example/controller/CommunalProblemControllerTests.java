package com.example.controller;

import static com.example.constants.UserConstants.PASSWORD_PRESIDENT;
import static com.example.constants.UserConstants.USERNAME_PRESIDENT;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.example.constants.CommunalProblemConstants.DESCRIPTION;
import static com.example.constants.CommunalProblemConstants.DESCRIPTION2;
import static com.example.constants.CommunalProblemConstants.ID_BUILDING;
import static com.example.constants.UserConstants.ID_COMPANY;
import static com.example.constants.CommunalProblemConstants.ID_BUILDING_NOT_FOUND;
import static com.example.constants.CommunalProblemConstants.ID_COMMUNAL_PROBLEM;
import static com.example.constants.CommunalProblemConstants.ID_APARTMENT;
import static com.example.constants.CommunalProblemConstants.ID_APARTMENT_NOT_FOUND;
import static com.example.constants.CommunalProblemConstants.ID_COMMUNAL_PROBLEM_NOT_FOUND;
import static com.example.constants.UserConstants.ID_NOT_FOUND;

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
import com.example.dto.CommunalProblemDTO;
import com.example.dto.LoginDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class CommunalProblemControllerTests {
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
		
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/login",
				new LoginDTO(USERNAME_PRESIDENT, PASSWORD_PRESIDENT), String.class);
		accessToken = responseEntity.getBody();
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddCommunalProblem() throws Exception{
		CommunalProblemDTO communalProblemDTO = new CommunalProblemDTO();
		communalProblemDTO.setDescription(DESCRIPTION);
		communalProblemDTO.setCompanyID(ID_COMPANY);
		
		String json = TestUtils.convertObjectToJson(communalProblemDTO);
		
		mockMvc.perform(post("/api/buildings/"  + ID_BUILDING + "/communalProblems")
				.header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isCreated());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddCommunalProblemCompanyIdNull() throws Exception{
		CommunalProblemDTO communalProblemDTO = new CommunalProblemDTO();
		communalProblemDTO.setDescription(DESCRIPTION);
		
		String json = TestUtils.convertObjectToJson(communalProblemDTO);
		
		mockMvc.perform(post("/api/buildings/"  + ID_BUILDING + "/communalProblems")
				.header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isCreated());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddCommunalProblemCompanyIdNotExist() throws Exception{
		CommunalProblemDTO communalProblemDTO = new CommunalProblemDTO();
		communalProblemDTO.setDescription(DESCRIPTION);
		communalProblemDTO.setCompanyID(ID_NOT_FOUND);
		
		String json = TestUtils.convertObjectToJson(communalProblemDTO);
		
		mockMvc.perform(post("/api/buildings/"  + ID_BUILDING + "/communalProblems")
				.header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isCreated());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddCommunalProblemBadRequest() throws Exception{
		CommunalProblemDTO communalProblemDTO = new CommunalProblemDTO();
		communalProblemDTO.setDescription(DESCRIPTION);
		communalProblemDTO.setCompanyID(ID_COMPANY);
		
		String json = TestUtils.convertObjectToJson(communalProblemDTO);
		
		mockMvc.perform(post("/api/buildings/"  + ID_BUILDING_NOT_FOUND + "/communalProblems")
				.header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void testGetCommunalProblem() throws Exception {
		mockMvc.perform(get("/api/communalProblems/" +1L ).header("X-Auth-Token", accessToken))
				.andExpect(status().isOk());

	}
	
	@Test
	public void testGetCommunalProblemNotFound() throws Exception {
		mockMvc.perform(get("/api/communalProblems/" +1000000L ).header("X-Auth-Token", accessToken))
				.andExpect(status().isNotFound());

	}
	
	@Test
	public void testGetCommunalProblemByBuilding() throws Exception {
		mockMvc.perform(get("/api/buildings/"+1L+"/communalProblems"  ).header("X-Auth-Token", accessToken))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.[*].description").value((DESCRIPTION2)))
				.andExpect(jsonPath("$.[*].id").value((1)));

	}
	
	@Test
	public void testGetCommunalProblemBadRequest() throws Exception {
		mockMvc.perform(get("/api/buildings/"+10000L+"/communalProblems"  ).header("X-Auth-Token", accessToken))
				.andExpect(status().isBadRequest());

	}
	
		
	
	@Test
	public void testGetCommunalProblemByBuildingActive() throws Exception {
		mockMvc.perform(get("/api/buildings/"+1L+"/communalProblems/active"  ).header("X-Auth-Token", accessToken))
				.andExpect(status().isOk());

	}
	
	@Test
	public void testGetCommunalProblemBadRequestActive() throws Exception {
		mockMvc.perform(get("/api/buildings/"+10000L+"/communalProblems/active"  ).header("X-Auth-Token", accessToken))
				.andExpect(status().isBadRequest());

	}
	
	@Test
	public void testGetCommunalProblemByBuildingCount() throws Exception {
		mockMvc.perform(get("/api/buildings/"+1L+"/communalProblems/count"  ).header("X-Auth-Token", accessToken))
				.andExpect(status().isOk());

	}
	
	
	@Test
	public void testGetCommunalProblemByBuildingActiveCount() throws Exception {
		mockMvc.perform(get("/api/buildings/"+1L+"/communalProblems/active/count"  ).header("X-Auth-Token", accessToken))
				.andExpect(status().isOk());

	}
	
	@Test
	public void testGetCommunalProblemBadRequestCount() throws Exception {
		mockMvc.perform(get("/api/buildings/"+10000L+"/communalProblems/count"  ).header("X-Auth-Token", accessToken))
				.andExpect(status().isBadRequest());

	}
	
	@Test
	public void testGetCommunalProblemBadRequestActiveCount() throws Exception {
		mockMvc.perform(get("/api/buildings/"+10000L+"/communalProblems/active/count"  ).header("X-Auth-Token", accessToken))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddCommunalProblemApartment() throws Exception{
		mockMvc.perform(put("/api/communalProblems/" + ID_COMMUNAL_PROBLEM +"/apartments/" + ID_APARTMENT)
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteCommunalProblemApartment() throws Exception{
		mockMvc.perform(put("/api/communalProblems/" + ID_COMMUNAL_PROBLEM +"/apartments/" + ID_APARTMENT)
				.header("X-Auth-Token", accessToken));
		
		mockMvc.perform(delete("/api/communalProblems/" + ID_COMMUNAL_PROBLEM +"/apartments/" + ID_APARTMENT)
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteCommunalProblemApartmentBadRequestProblem() throws Exception{
	
		mockMvc.perform(delete("/api/communalProblems/" + ID_COMMUNAL_PROBLEM +"/apartments/" + ID_APARTMENT_NOT_FOUND)
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteCommunalProblemApartmentBadRequestApartment() throws Exception{
	
		mockMvc.perform(delete("/api/communalProblems/" + ID_COMMUNAL_PROBLEM_NOT_FOUND +"/apartments/" + ID_APARTMENT)
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());		
	}
	
	
	@Test
	public void testAddCommunalProblemApartmentBadId() throws Exception{
		mockMvc.perform(put("/api/communalProblems/" + ID_COMMUNAL_PROBLEM_NOT_FOUND +"/apartments/" + ID_APARTMENT)
				.header("X-Auth-Token", accessToken).contentType(contentType))
		.andExpect(status().isBadRequest());		
	}
	
	@Test
	public void testAddCommunalProblemApartmentBadApartmentId() throws Exception{
		mockMvc.perform(put("/api/communalProblems/" + ID_COMMUNAL_PROBLEM +"/apartments/" + ID_APARTMENT_NOT_FOUND)
				.header("X-Auth-Token", accessToken).contentType(contentType))
		.andExpect(status().isBadRequest());		
	}
	
	
	

}
