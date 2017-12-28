package com.example.controller;

import static com.example.constants.UserConstants.PASSWORD;
import static com.example.constants.UserConstants.USERNAME;
import static com.example.constants.UserConstants.USERNAME_PRESIDENT;
import static com.example.constants.UserConstants.PASSWORD_PRESIDENT;
import static com.example.constants.UserConstants.ID_USER;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.example.constants.UserConstants.ID_COMPANY;
import static com.example.constants.GlitchConstants.NEW_DESCRIPTION;
import static com.example.constants.ApartmentConstants.ID_APARTMENT;
import static com.example.constants.GlitchConstants.STATE_REPORTED;
import static com.example.constants.GlitchConstants.ID;
import static com.example.constants.GlitchConstants.ID_NOT_FOUND;
import static com.example.constants.ApartmentConstants.ID_APARTMENT_NOT_FOUND;
import static com.example.constants.GlitchConstants.*;
import static com.example.constants.BuildingConstatnts.PAGE_SIZE;
import static com.example.constants.UserConstants.ID_PRESIDENT;
import static com.example.constants.GlitchConstants.ID_GLITCH;
import static com.example.constants.GlitchConstants.DESCRIPTION;
import static com.example.constants.UserConstants.USERNAME_NOT_EXIST;

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
import com.example.dto.GlitchDTO;
import com.example.dto.LoginDTO;
import com.example.dto.UserDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class GlitchControllerTest {
	private String accessTokenTenant;
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
				new LoginDTO(USERNAME, PASSWORD), String.class);
		accessTokenTenant = responseEntity.getBody();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddGlitch() throws Exception {
		GlitchDTO glitchDTO = new GlitchDTO(ID_COMPANY, NEW_DESCRIPTION);

		String json = TestUtils.convertObjectToJson(glitchDTO);

		mockMvc.perform(post("/api/apartments/" + ID_APARTMENT + "/glitches").header("X-Auth-Token", accessTokenTenant)
				.contentType(contentType).content(json)).andExpect(status().isCreated())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.apartment.id").value(ID_APARTMENT))
				.andExpect(jsonPath("$.description").value(NEW_DESCRIPTION))
				.andExpect(jsonPath("$.state.state").value(STATE_REPORTED))
				.andExpect(jsonPath("$.responsiblePerson.username").value(USERNAME_PRESIDENT));
	}

	@Test
	public void testAddGlitchBadRequest() throws Exception {
		GlitchDTO glitchDTO = new GlitchDTO(ID_COMPANY, NEW_DESCRIPTION);

		String json = TestUtils.convertObjectToJson(glitchDTO);

		mockMvc.perform(post("/api/apartments/" + ID_APARTMENT_NOT_FOUND + "/glitches")
				.header("X-Auth-Token", accessTokenTenant).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());
	}

	@Before
	public void loginPresident() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/login",
				new LoginDTO(USERNAME_PRESIDENT, PASSWORD_PRESIDENT), String.class);
		accessTokenPresident = responseEntity.getBody();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testChangeResponsiblePerson() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(USERNAME);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(put("/api/glitches/" + ID + "/responsiblePerson").header("X-Auth-Token", accessTokenPresident)
				.contentType(contentType).content(json)).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.responsiblePerson.id").value(ID_USER.intValue()))
				.andExpect(jsonPath("$.responsiblePerson.username").value(USERNAME));

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testChangeResponsiblePersonBadRequest() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(USERNAME);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(put("/api/glitches/" + ID_NOT_FOUND + "/responsiblePerson")
				.header("X-Auth-Token", accessTokenPresident).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testChangeResponsiblePersonNotExistUsername() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(USERNAME_NOT_EXIST);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(put("/api/glitches/" + ID + "/responsiblePerson").header("X-Auth-Token", accessTokenPresident)
				.contentType(contentType).content(json)).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.responsiblePerson.id").value(ID_PRESIDENT.intValue()))
				.andExpect(jsonPath("$.responsiblePerson.username").value(USERNAME_PRESIDENT));

	}

	@Test
	public void testGetGlitches() throws Exception {
		mockMvc.perform(get("/api/glitches?page=0&size=" + PAGE_SIZE).header("X-Auth-Token", accessTokenTenant))
				.andExpect(status().isOk()).andExpect(jsonPath("$.[*].id").value(hasItem(ID_GLITCH.intValue())))
				.andExpect(jsonPath("$.[*].description").value(hasItem(DESCRIPTION)));

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testChangeCompany() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(USERNAME);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(put("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH + "/company/" + ID_COMPANY)
				.header("X-Auth-Token", accessTokenPresident).contentType(contentType).content(json))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.companyID").value(ID_COMPANY.intValue()));

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testChangeCompanyBadRequestApartmnet() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(USERNAME);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(
				put("/api/apartments/" + ID_APARTMENT_NOT_FOUND + "/glitches/" + ID_GLITCH + "/company/" + ID_COMPANY)
						.header("X-Auth-Token", accessTokenPresident).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testChangeCompanyBadRequestGlitches() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(USERNAME);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(
				put("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH_NOT_FOUND + "/company/" + ID_COMPANY)
						.header("X-Auth-Token", accessTokenPresident).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testChangeCompanyBadRequestCompany() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(USERNAME);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(
				put("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH + "/company/" + ID_COMPANY_NOT_FOUND)
						.header("X-Auth-Token", accessTokenPresident).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testChangeGlitchDateApprove() throws Exception {

		mockMvc.perform(put("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH).header("X-Auth-Token",
				accessTokenPresident)).andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.dateOfRepairApproved").value(true));

	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testChangeGlitchDateApproveUnauthorized() throws Exception {

		mockMvc.perform(put("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH).header("X-Auth-Token",
				accessTokenTenant)).andExpect(status().isUnauthorized());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testChangeGlitchDateApproveBadRequestApartment() throws Exception {

		mockMvc.perform(put("/api/apartments/" + ID_APARTMENT_NOT_FOUND + "/glitches/" + ID_GLITCH)
				.header("X-Auth-Token", accessTokenPresident)).andExpect(status().isBadRequest());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testChangeGlitchDateApproveBadRequestGlitch() throws Exception {

		mockMvc.perform(put("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH_NOT_FOUND)
				.header("X-Auth-Token", accessTokenPresident)).andExpect(status().isBadRequest());

	}
	
	

	@Test
	@Transactional
	@Rollback(true)
	public void testChangeGlitchPhoto() throws Exception {

		mockMvc.perform(
				put("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH + "/photo?image=" + IMAGE_UPLOAD)
						.header("X-Auth-Token", accessTokenTenant))
				.andExpect(status().isOk());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testChangeGlitchPhotoBadRequstApartment() throws Exception {

		mockMvc.perform(put(
				"/api/apartments/" + ID_APARTMENT_NOT_FOUND + "/glitches/" + ID_GLITCH + "/photo?image=" + IMAGE_UPLOAD)
						.header("X-Auth-Token", accessTokenTenant))
				.andExpect(status().isBadRequest());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testChangeGlitchPhotoBadRequstGlitch() throws Exception {

		mockMvc.perform(put("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH_NOT_FOUND + "/photo?image=" + IMAGE_UPLOAD)
				.header("X-Auth-Token", accessTokenTenant)).andExpect(status().isBadRequest());

	}

	@Test
	public void testGetCompany() throws Exception {

		mockMvc.perform(get("/api/glitches/" + ID_GLITCH + "/company").header("X-Auth-Token", accessTokenPresident))
				.andExpect(status().isOk()).andExpect(jsonPath("$.[*].id").value(hasItem(ID_GLITCH.intValue())));

	}
	
	@Test
	public void testGetCompanyBadRequest() throws Exception {

		mockMvc.perform(get("/api/glitches/" + ID_GLITCH_NOT_FOUND + "/company").header("X-Auth-Token", accessTokenPresident))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void testSetCompany() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(USERNAME);
		userDTO.setId(6L);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(put("/api/glitches/" + ID_GLITCH_2 + "/company").header("X-Auth-Token", accessTokenPresident)
				.contentType(contentType).content(json)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(ID_GLITCH_2))
				.andExpect(jsonPath("$.description").value(DESCRIPTION_2));

	}

	@Test
	public void testSetCompanyBadRequest() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(USERNAME);
		userDTO.setId(6L);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(put("/api/glitches/" + ID_GLITCH_NOT_FOUND + "/company")
				.header("X-Auth-Token", accessTokenPresident).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void testSetCompanBadRequesty() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(USERNAME);
		userDTO.setId(6L);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(put("/api/glitches/" + ID_GLITCH_NOT_FOUND + "/company")
				.header("X-Auth-Token", accessTokenPresident).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

}
