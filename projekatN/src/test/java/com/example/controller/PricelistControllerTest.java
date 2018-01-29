package com.example.controller;

import static com.example.constants.PricelistConstants.*;
import static com.example.constants.PricelistConstants.ID_NOT_FOUND;
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
import com.example.dto.ItemInPricelistDTO;
import com.example.dto.LoginDTO;
import com.example.dto.PricelistDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class PricelistControllerTest {

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
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain)
				.build();

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/login",
				new LoginDTO(USERNAME_COMPANY, USERNAME_COMPANY), String.class);
		accessToken = responseEntity.getBody();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddPricelist() throws Exception {
		PricelistDTO pricelistDTO = new PricelistDTO(NEW_ID, new Date());

		String json = TestUtils.convertObjectToJson(pricelistDTO);

		mockMvc.perform(post("/api/company/" + COMPANY_ID + "/pricelist").header("X-Auth-Token", accessToken)
				.contentType(contentType).content(json)).andExpect(status().isCreated())
				.andExpect(content().contentType(contentType));

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddPricelistBadRequest() throws Exception {
		PricelistDTO pricelistDTO = new PricelistDTO(NEW_ID, new Date());

		String json = TestUtils.convertObjectToJson(pricelistDTO);

		mockMvc.perform(post("/api/company/" + COMPANY_ID_NOT_FOUND + "/pricelist").header("X-Auth-Token", accessToken)
				.contentType(contentType).content(json)).andExpect(status().isBadRequest());

	}

	@Test
	public void testGetPricelist() throws Exception {
		mockMvc.perform(get("/api/company/" + NEW_COMPANY_ID + "/pricelist").header("X-Auth-Token", accessToken))
				.andExpect(status().isCreated());

	}

	@Test
	public void testGetPricelistBadRequest() throws Exception {
		mockMvc.perform(get("/api/company/" + COMPANY_ID_NOT_FOUND + "/pricelist").header("X-Auth-Token", accessToken))
				.andExpect(status().isBadRequest());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddItemInPricelist() throws Exception {
		ItemInPricelistDTO itemDTO = new ItemInPricelistDTO(NEW_ID, NEW_NAME, NEW_PRICE);
		String json = TestUtils.convertObjectToJson(itemDTO);

		mockMvc.perform(post("/api/company/" + COMPANY_ID + "/pricelist/items").header("X-Auth-Token", accessToken)
				.contentType(contentType).content(json)).andExpect(status().isCreated())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.nameOfType").value(NEW_NAME))
				.andExpect(jsonPath("$.price").value(NEW_PRICE));

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddItemInPricelistBadRequest() throws Exception {
		ItemInPricelistDTO itemDTO = new ItemInPricelistDTO(NEW_ID, NEW_NAME, NEW_PRICE);
		String json = TestUtils.convertObjectToJson(itemDTO);

		mockMvc.perform(post("/api/company/" + COMPANY_ID_NOT_FOUND + "/pricelist/items")
				.header("X-Auth-Token", accessToken).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void testGetPricelistItems() throws Exception {
		mockMvc.perform(get("/api/company/" + COMPANY_ID + "/pricelist/items").header("X-Auth-Token", accessToken))
				.andExpect(status().isOk());

	}

	@Test
	public void testGetPricelistItemsBadRequest() throws Exception {
		mockMvc.perform(get("/api/company/" + 100000L + "/pricelist/items").header("X-Auth-Token", accessToken))
				.andExpect(status().isBadRequest());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeletePricelistItems() throws Exception {
		mockMvc.perform(
				delete("/api/company/" + COMPANY_ID + "/pricelist/items/" + ID).header("X-Auth-Token", accessToken))
				.andExpect(status().isOk());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeletePricelistItemsBadRequest() throws Exception {
		mockMvc.perform(delete("/api/company/" + COMPANY_ID_NOT_FOUND + "/pricelist/items/" + ID_NOT_FOUND)
				.header("X-Auth-Token", accessToken)).andExpect(status().isBadRequest());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateItemInPricelist() throws Exception {
		ItemInPricelistDTO itemDTO = new ItemInPricelistDTO(ID, NEW_NAME, PRICE);
		String json = TestUtils.convertObjectToJson(itemDTO);

		mockMvc.perform(put("/api/company/" + COMPANY_ID + "/pricelist/items/" + ID).header("X-Auth-Token", accessToken)
				.contentType(contentType).content(json)).andExpect(status().isOk());

	}

	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateItemInPricelistBadRequest() throws Exception {
		ItemInPricelistDTO itemDTO = new ItemInPricelistDTO(NEW_ID, NEW_NAME, NEW_PRICE);
		String json = TestUtils.convertObjectToJson(itemDTO);

		mockMvc.perform(put("/api/company/" + COMPANY_ID_NOT_FOUND + "/pricelist/items/" + ID)
				.header("X-Auth-Token", accessToken).contentType(contentType).content(json))
				.andExpect(status().isBadRequest());

	}
	
	@Test
	public void testUpdatePricelistItems() throws Exception {
		mockMvc.perform(put("/api/company/" + COMPANY_ID + "/pricelist/types/"+1L).header("X-Auth-Token", accessToken))
				.andExpect(status().isOk());

	}
	
	@Test
	public void testUpdatePricelistItemsBadStatur() throws Exception {
		mockMvc.perform(put("/api/company/" + 10000L + "/pricelist/types/"+1L).header("X-Auth-Token", accessToken))
				.andExpect(status().isBadRequest());

	}


}
