package com.example.controller;

import static com.example.constants.ApartmentConstants.DESCRIPTION;
import static com.example.constants.ApartmentConstants.ID_APARTMENT;
import static com.example.constants.ApartmentConstants.ID_BUILDING;
import static com.example.constants.ApartmentConstants.NUMBER;
import static com.example.constants.ApartmentConstants.PAGE_SIZE;
import static com.example.constants.NotificationConstants.*;
import static com.example.constants.UserConstants.PASSWORD_ADMIN;
import static com.example.constants.UserConstants.PASSWORD_PRESIDENT;
import static com.example.constants.UserConstants.USERNAME_ADMIN;
import static com.example.constants.UserConstants.USERNAME_PRESIDENT;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.example.dto.AgendaItemDTO;
import com.example.dto.LoginDTO;
import com.example.dto.NotificationDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class NotificationControllerTest {
	
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
	public void testNotification() throws Exception{
		
		Date new_date=new Date();
		NotificationDTO notificationDTO = new NotificationDTO(ID_NEW , new_date, NEW_TEXT);
	
		String json = TestUtils.convertObjectToJson(notificationDTO);
		
		mockMvc.perform(post("/api/buildings/"+BUILDING_ID+"/notifications")
							.header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.text").value(NEW_TEXT))
		.andExpect(jsonPath("$.date").value(new_date));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddNotificationBadRequest() throws Exception{
		
		Date new_date=new Date();
		NotificationDTO notificationDTO = new NotificationDTO(ID_NEW , new_date, NEW_TEXT);
	
		String json = TestUtils.convertObjectToJson(notificationDTO);
		
		mockMvc.perform(post("/api/buildings/"+BUILDING_ID_NOT_FOUND+"/notifications")
							.header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testGetNotifications() throws Exception{
		mockMvc.perform(get("/api/buildings/" + ID_BUILDING + 
				"/notifications?page=0&size="+PAGE_SIZE )
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$",  hasSize(1)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(ID.intValue())))
		.andExpect(jsonPath("$.[*].text").value(hasItem(TEXT)));
		
	}
	
	@Test
	public void testGetNotification() throws Exception{
		mockMvc.perform(get("/api/buildings/" + ID_BUILDING + 
				"/notifications/"+ID)
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(ID.intValue()))
		.andExpect(jsonPath("$.text").value(TEXT));
		
	}
	
	

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteNotification() throws Exception{
		mockMvc.perform(delete("/api/buildings/" + ID_BUILDING + 
				"/notifications/"+ID)
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteNotificationBadRequest() throws Exception{
		mockMvc.perform(delete("/api/buildings/" + BUILDING_ID_NOT_FOUND + 
				"/notifications/"+ID)
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteNotificationNotFound() throws Exception{
		mockMvc.perform(delete("/api/buildings/" + ID_BUILDING + 
				"/notifications/"+ID_NOT_FOUND)
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isNotFound());
		
	}
	
	
	@Test
	public void testGetNotificationNotFound() throws Exception{
		mockMvc.perform(get("/api/buildings/" + ID_BUILDING + 
				"/notifications/"+ID_NEW)
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void testGetNotificationBadRequest() throws Exception{
		mockMvc.perform(get("/api/buildings/" + BUILDING_ID_NOT_FOUND + 
				"/notifications/"+ID)
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void testGetOwnNotification() throws Exception{
		mockMvc.perform(get("/api/notifications")
				.header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
		
	}
}
