package com.example.controller;

import static com.example.constants.UserConstants.EMAIL;
import static com.example.constants.ApartmentConstants.ID_NOT_FOUND;
import static com.example.constants.UserConstants.ID_USER;
import static com.example.constants.UserConstants.NEW_USERNAME;
import static com.example.constants.UserConstants.PASSWORD_ADMIN;
import static com.example.constants.UserConstants.USERNAME;
import static com.example.constants.UserConstants.USERNAME_ADMIN;
import static com.example.constants.ApartmentConstants.ID_APARTMENT;
import static com.example.constants.ApartmentConstants.ID_BUILDING;
import static com.example.constants.ApartmentConstants.PAGE_SIZE;
import static com.example.constants.ApartmentConstants.DESCRIPTION;
import static com.example.constants.ApartmentConstants.NUMBER;
import static com.example.constants.ApartmentConstants.NEW_NUMBER;
import static com.example.constants.ApartmentConstants.NEW_DESCRIPTION;
import static com.example.constants.ApartmentConstants.ID_APARTMENT_NOT_FOUND;
import static com.example.constants.ApartmentConstants.ID_BUILDING_NOT_FOUND;
import static com.example.constants.BuildingConstatnts.STREET;
import static com.example.constants.BuildingConstatnts.CITY;
import static com.example.constants.ApartmentConstants.BUILDING_NUMBER;
import static com.example.constants.UserConstants.ID_OWNER;
import static com.example.constants.UserConstants.USERNAME_OWNER;

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.TestUtils;
import com.example.dto.ApartmentDTO;
import com.example.dto.LoginDTO;
import com.example.dto.UserDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ApartmentControllerTest {

	private String accessToken;
	private String accessTokenOwner;

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
				new LoginDTO("vodovod", "vodovod"), String.class);
		accessTokenOwner = responseEntity2.getBody();
	}

	@Test
	public void testGetApartments() throws Exception {
		mockMvc.perform(get("/api/buildings/" + ID_BUILDING + "/apartments?page=0&size=" + PAGE_SIZE)
				.header("X-Auth-Token", accessToken)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(PAGE_SIZE)))
				.andExpect(jsonPath("$.[*].id").value(hasItem(ID_APARTMENT.intValue())))
				.andExpect(jsonPath("$.[*].description").value(hasItem(DESCRIPTION)))
				.andExpect(jsonPath("$.[*].number").value(hasItem(NUMBER)));

	}

	@Test
	public void testGetApartment() throws Exception {
		mockMvc.perform(get("/api/apartments/" + ID_APARTMENT.intValue()).header("X-Auth-Token", accessToken))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.description").value(DESCRIPTION)).andExpect(jsonPath("$.number").value(NUMBER));

	}

	@Test
	public void testGetApartmentNotFound() throws Exception {
		mockMvc.perform(get("/api/apartments/" + ID_APARTMENT_NOT_FOUND.intValue()).header("X-Auth-Token", accessToken))
				.andExpect(status().isNotFound());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddApartment() throws Exception {
		ApartmentDTO apartmentDTO = new ApartmentDTO(NEW_NUMBER, NEW_DESCRIPTION);

		String json = TestUtils.convertObjectToJson(apartmentDTO);

		mockMvc.perform(post("/api/buildings/" + ID_BUILDING + "/apartments").header("X-Auth-Token", accessToken)
				.contentType(contentType).content(json)).andExpect(status().isCreated())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.number").value(NEW_NUMBER))
				.andExpect(jsonPath("$.description").value(NEW_DESCRIPTION));
	}

	@Test
	public void testAddApartmentBadRequest() throws Exception {
		ApartmentDTO apartmentDTO = new ApartmentDTO(NEW_NUMBER, NEW_DESCRIPTION);

		String json = TestUtils.convertObjectToJson(apartmentDTO);

		mockMvc.perform(post("/api/buildings/" + ID_BUILDING_NOT_FOUND + "/apartments")
				.header("X-Auth-Token", accessToken).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateApartment() throws Exception {
		ApartmentDTO apartmentDTO = new ApartmentDTO(ID_APARTMENT, NEW_NUMBER, NEW_DESCRIPTION);

		String json = TestUtils.convertObjectToJson(apartmentDTO);

		mockMvc.perform(
				put("/api/apartments").header("X-Auth-Token", accessToken).contentType(contentType).content(json))
				.andExpect(status().isOk()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.number").value(NEW_NUMBER))
				.andExpect(jsonPath("$.description").value(NEW_DESCRIPTION));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateApartmentBadRequest() throws Exception {
		ApartmentDTO apartmentDTO = new ApartmentDTO(ID_APARTMENT_NOT_FOUND, NEW_NUMBER, NEW_DESCRIPTION);

		String json = TestUtils.convertObjectToJson(apartmentDTO);

		mockMvc.perform(
				put("/api/apartments").header("X-Auth-Token", accessToken).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteApartment() throws Exception {
		mockMvc.perform(delete("/api/apartments/" + 2).header("X-Auth-Token", accessToken))
				.andExpect(status().isOk());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteApartmentNotFound() throws Exception {
		mockMvc.perform(delete("/api/apartments/" + ID_APARTMENT_NOT_FOUND).header("X-Auth-Token", accessToken))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testFindByAddress() throws Exception {
		mockMvc.perform(get("/api/apartment?street=" + STREET + "&number=" + BUILDING_NUMBER + "&city=" + CITY
				+ "&number_apartment=" + NUMBER).header("X-Auth-Token", accessToken)).andExpect(status().isOk());

	}

	@Test
	public void testFindByAddressNotFound() throws Exception {
		mockMvc.perform(get("/api/apartment?street=" + STREET + "&number=" + BUILDING_NUMBER + "&city=" + CITY
				+ "&number_apartment=" + NEW_NUMBER).header("X-Auth-Token", accessToken))
				.andExpect(status().isNotFound());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddOwner() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(ID_USER);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(post("/api/apartments/ " + ID_APARTMENT + "/owner").header("X-Auth-Token", accessToken)
				.contentType(contentType).content(json)).andExpect(status().isCreated())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.id").value(ID_APARTMENT))
				.andExpect(jsonPath("$.owner.id").value(ID_USER))
				.andExpect(jsonPath("$.owner.username").value(USERNAME))
				.andExpect(jsonPath("$.owner.email").value(EMAIL));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteOwner() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(ID_USER);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(delete("/api/apartments/ " + ID_APARTMENT + "/owner").header("X-Auth-Token", accessToken)
				.contentType(contentType).content(json)).andExpect(status().isOk());
	}


	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteOwnerBD() throws Exception {

		mockMvc.perform(delete("/api/apartments/ " + 200000L + "/owner").header("X-Auth-Token", accessToken)
				).andExpect(status().isBadRequest());
	}

	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddOwnerBadUserId() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(ID_NOT_FOUND);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(post("/api/apartments/ " + ID_APARTMENT + "/owner").header("X-Auth-Token", accessToken)
				.contentType(contentType).content(json)).andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddOwnerAddNewUser() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(NEW_USERNAME);
		userDTO.setId(null);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(post("/api/apartments/ " + ID_APARTMENT + "/owner").header("X-Auth-Token", accessToken)
				.contentType(contentType).content(json)).andExpect(status().isCreated())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.id").value(ID_APARTMENT))
				.andExpect(jsonPath("$.owner.username").value(NEW_USERNAME));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddOwnerAddNewUserBadRequest() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(NEW_USERNAME);
		userDTO.setId(null);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(post("/api/apartments/ " + ID_APARTMENT_NOT_FOUND + "/owner")
				.header("X-Auth-Token", accessToken).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddOwnerExist() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(ID_OWNER);

		String json = TestUtils.convertObjectToJson(userDTO);

		mockMvc.perform(post("/api/apartments/ " + ID_APARTMENT + "/owner").header("X-Auth-Token", accessToken)
				.contentType(contentType).content(json)).andExpect(status().isCreated())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.id").value(ID_APARTMENT))
				.andExpect(jsonPath("$.owner.id").value(ID_OWNER))
				.andExpect(jsonPath("$.owner.username").value(USERNAME_OWNER));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteApartmentsNF() throws Exception {

		mockMvc.perform(delete("/api/apartments/ " + 100000L ).header("X-Auth-Token", accessToken)).
				andExpect(status().isNotFound());
			
	}
	

	@Test
	@Transactional
	@Rollback(true)
	public void MyApartments() throws Exception {

		mockMvc.perform(get("/api/apartments/my").header("X-Auth-Token", accessToken)).andExpect(status().isOk());
		
		mockMvc.perform(get("/api/apartments/owner/my").header("X-Auth-Token", accessToken)
				).andExpect(status().isOk());
	}


}
