package com.example.controller;

import static com.example.constants.ApartmentConstants.ID_APARTMENT;
import static com.example.constants.ApartmentConstants.ID_APARTMENT_NOT_FOUND;

import static com.example.constants.BillConstants.*;
import static com.example.constants.UserConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Date;

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
import com.example.dto.BillDTO;
import com.example.dto.LoginDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class BillControllerTest {

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
				new LoginDTO(USERNAME_COMPANY, PASSWORD_COMPANY), String.class);
		accessToken = responseEntity.getBody();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddBill() throws Exception {
		Date new_date = new Date();
		BillDTO billDTO = new BillDTO(NEW_ID, NEW_PRICE, new_date, NEW_APPROVED);

		String json = TestUtils.convertObjectToJson(billDTO);

		mockMvc.perform(post("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH + "/bill")
				.header("X-Auth-Token", accessToken).contentType(contentType).content(json))
				.andExpect(status().isCreated()).andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.approved").value(NEW_APPROVED)).andExpect(jsonPath("$.date").value(new_date));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddBillBadRequestApartment() throws Exception {
		Date new_date = new Date();
		BillDTO billDTO = new BillDTO(NEW_ID, NEW_PRICE, new_date, NEW_APPROVED);

		String json = TestUtils.convertObjectToJson(billDTO);

		mockMvc.perform(post("/api/apartments/" + ID_APARTMENT_NOT_FOUND + "/glitches/" + ID_GLITCH + "/bill")
				.header("X-Auth-Token", accessToken).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddBillBadRequestGlitches() throws Exception {
		Date new_date = new Date();
		BillDTO billDTO = new BillDTO(NEW_ID, NEW_PRICE, new_date, NEW_APPROVED);

		String json = TestUtils.convertObjectToJson(billDTO);

		mockMvc.perform(post("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH_NOT_FOUND + "/bill")
				.header("X-Auth-Token", accessToken).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());
		}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteBill() throws Exception {
/*
		mockMvc.perform(delete("/api/apartments/" + 1L + "/glitches/" + 1L + "/bill")
				.header("X-Auth-Token", accessToken).contentType(contentType)).andExpect(status().isOk());*/
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteBillNotFound() throws Exception {

		mockMvc.perform(delete("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH_WITHOUT_BILL + "/bill")
				.header("X-Auth-Token", accessToken).contentType(contentType)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteBillBadRequest() throws Exception {

		mockMvc.perform(delete("/api/apartments/" + ID_APARTMENT_NOT_FOUND + "/glitches/" + ID_GLITCH + "/bill")
				.header("X-Auth-Token", accessToken).contentType(contentType)).andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteBillBadRequestGlitch() throws Exception {

		mockMvc.perform(delete("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH_NOT_FOUND + "/bill")
				.header("X-Auth-Token", accessToken).contentType(contentType)).andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteBill2() throws Exception {
		mockMvc.perform(delete("/api/bills/" + ID).header("X-Auth-Token", accessToken).contentType(contentType))
				.andExpect(status().isOk());
	}

	public void testDeleteBill2NotFound() throws Exception {
		mockMvc.perform(delete("/api/bills/" + NEW_ID).header("X-Auth-Token", accessToken).contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testGetBill() throws Exception {
		mockMvc.perform(get("/api/bills/" + ID).header("X-Auth-Token", accessToken)).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.id").value(ID))
				.andExpect(jsonPath("$.approved").value(APPROVED_2));

	}

	@Test
	public void testGetBillNotFound() throws Exception {
		mockMvc.perform(get("/api/bills/" + NEW_ID).header("X-Auth-Token", accessToken))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testGetBill2() throws Exception {
		/*mockMvc.perform(get("/api/apartments/" + 1L + "/glitches/" + 1L + "/bill")
				.header("X-Auth-Token", accessToken)).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.id").value(ID))
				.andExpect(jsonPath("$.approved").value(APPROVED_2));
*/
	}
	
	@Test
	public void testGetCompanies() throws Exception {
		mockMvc.perform(get("/api/companies/" + 1L + "/bills")
				.header("X-Auth-Token", accessToken)).andExpect(status().isOk());

	}
	
	@Test
	public void testGetUsers() throws Exception {
		mockMvc.perform(get("/api/users/" + 1L + "/bills")
				.header("X-Auth-Token", accessTokenPresident)).andExpect(status().isOk());

	}
	
	@Test
	public void testGetCompaniesCount() throws Exception {
		mockMvc.perform(get("/api/companies/" + 1L + "/bills/count")
				.header("X-Auth-Token", accessToken)).andExpect(status().isOk());

	}
	
	@Test
	public void testGetUsersCounr() throws Exception {
		mockMvc.perform(get("/api/users/" + 1L + "/bills/count")
				.header("X-Auth-Token", accessToken)).andExpect(status().isOk());

	}

	@Test
	public void testGetCompaniesCountBR() throws Exception {
		mockMvc.perform(get("/api/companies/" + 100000L + "/bills/count")
				.header("X-Auth-Token", accessToken)).andExpect(status().isBadRequest());

	}
	
	@Test
	public void testGetUsersCounrBR() throws Exception {
		mockMvc.perform(get("/api/users/" + 100000L + "/bills/count")
				.header("X-Auth-Token", accessToken)).andExpect(status().isBadRequest());

	}
	
	@Test
	public void testGetUsersBR() throws Exception {
		mockMvc.perform(get("/api/users/" + 100000L + "/bills")
				.header("X-Auth-Token", accessTokenPresident)).andExpect(status().isBadRequest());

	}

	@Test
	public void testGetBill2NotFound() throws Exception {
		mockMvc.perform(get("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH_WITHOUT_BILL + "/bill")
				.header("X-Auth-Token", accessToken)).andExpect(status().isNotFound());
	}

	@Test
	public void testGetBill2BadRequest() throws Exception {
		mockMvc.perform(get("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH_NOT_FOUND + "/bill")
				.header("X-Auth-Token", accessToken)).andExpect(status().isBadRequest());
	}

	@Test
	public void testGetBill2BadRequestApartmenr() throws Exception {
		mockMvc.perform(get("/api/apartments/" + ID_APARTMENT_NOT_FOUND + "/glitches/" + ID_GLITCH + "/bill")
				.header("X-Auth-Token", accessToken)).andExpect(status().isBadRequest());
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
	public void testSetBillApprove() throws Exception {
		/*mockMvc.perform(put("/api/apartments/" + 1L + "/glitches/" + 1L + "/bill")
				.header("X-Auth-Token", accessTokenPresident)).andExpect(status().isOk())
				.andExpect(jsonPath("$.approved").value(true));*/
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSetBillApproveBadRequestApartmant() throws Exception {
		mockMvc.perform(put("/api/apartments/" + ID_APARTMENT_NOT_FOUND + "/glitches/" + ID_GLITCH + "/bill")
				.header("X-Auth-Token", accessTokenPresident)).andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSetBillApproveBadRequestGlitch() throws Exception {
		mockMvc.perform(put("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH_NOT_FOUND + "/bill")
				.header("X-Auth-Token", accessTokenPresident)).andExpect(status().isBadRequest());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSetBillApproveNotFound() throws Exception {
		mockMvc.perform(put("/api/apartments/" + ID_APARTMENT + "/glitches/" + ID_GLITCH_WITHOUT_BILL + "/bill")
				.header("X-Auth-Token", accessTokenPresident)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSetBillApprove2() throws Exception {
		mockMvc.perform(put("/api/bills/" + ID).header("X-Auth-Token", accessTokenPresident)).andExpect(status().isOk())
				.andExpect(jsonPath("$.approved").value(true));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSetBillApprove2NotFound() throws Exception {
		mockMvc.perform(put("/api/bills/" + NEW_ID).header("X-Auth-Token", accessTokenPresident))
				.andExpect(status().isNotFound());
	}
}