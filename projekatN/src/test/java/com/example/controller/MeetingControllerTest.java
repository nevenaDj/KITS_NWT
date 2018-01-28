package com.example.controller;

import static com.example.constants.UserConstants.PASSWORD_PRESIDENT;
import static com.example.constants.UserConstants.USERNAME_PRESIDENT;
import static com.example.constants.BuildingConstatnts.BUILDING_ID_1;
import static com.example.constants.MeetingConstants.*;
import static com.example.constants.AgendaItemConstants.ID_MEETING;
import static com.example.constants.ApartmentConstants.ID_BUILDING_NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
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
import com.example.dto.LoginDTO;
import com.example.dto.MeetingDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class MeetingControllerTest {
	
	/*private String accessToken;

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
	}
	
	@Before
	public void loginPresident() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/login",
				new LoginDTO(USERNAME_PRESIDENT, PASSWORD_PRESIDENT), String.class);
		accessToken = responseEntity.getBody();
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddMeeting() throws Exception{
		Date dateAndTime = new SimpleDateFormat("yyyy-MM-dd").parse("2017-12-12");
		MeetingDTO meetingDTO = new MeetingDTO(dateAndTime);
		
		String json = TestUtils.convertObjectToJson(meetingDTO);
		
		mockMvc.perform(post("/api/buildings/" + BUILDING_ID_1 + "/meetings").header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isCreated());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddMeetingBadRequest() throws Exception{
		Date dateAndTime = new SimpleDateFormat("yyyy-MM-dd").parse("2017-12-12");
		MeetingDTO meetingDTO = new MeetingDTO(dateAndTime);
		
		String json = TestUtils.convertObjectToJson(meetingDTO);
		
		mockMvc.perform(post("/api/buildings/" + ID_BUILDING_NOT_FOUND + "/meetings").header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isBadRequest());
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetMeetingsBadRequest() throws Exception{
		
		mockMvc.perform(get("/api/buildings/" + ID_BUILDING_NOT_FOUND + "/meetings").header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testGetMeetingByDateBadRequest() throws Exception{
		Date dateAndTime = new SimpleDateFormat("yyyy-MM-dd").parse("2017-11-14");
		
		mockMvc.perform(get("/api/buildings/"+BUILDING_ID_1+"/meetings?date="+dateAndTime).header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void testGetDatesOfMeeting() throws Exception{	
		mockMvc.perform(get("/api/buildings/"+BUILDING_ID_1+"/meetings/dates").header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
		
	}
	
	
	@Test
	public void testGetDatesOfMeetingBadRequest() throws Exception{	
		mockMvc.perform(get("/api/buildings/"+ID_BUILDING_NOT_FOUND+"/meetings/dates").header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSetMeetingActive() throws Exception{

		mockMvc.perform(put("/api/buildings/"+BUILDING_ID_1+"/meeting/"+ID_MEETING+"/active").header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.active").value(true));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSetMeetingActiveBadRequest() throws Exception{

		mockMvc.perform(put("/api/buildings/"+ID_BUILDING_NOT_FOUND+"/meeting/"+ID_MEETING+"/active").header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSetMeetingActiveBadRequestMeeting() throws Exception{

		mockMvc.perform(put("/api/buildings/"+BUILDING_ID_1+"/meeting/"+ID_MEETING_NOT_FOUND+"/active").header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSetMeetingDeActive() throws Exception{

		mockMvc.perform(put("/api/buildings/"+BUILDING_ID_1+"/meeting/"+ID_MEETING+"/deactive").header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.active").value(false));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSetMeetingDeActiveBadRequest() throws Exception{

		mockMvc.perform(put("/api/buildings/"+ID_BUILDING_NOT_FOUND+"/meeting/"+ID_MEETING+"/deactive").header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSetMeetingDeActiveBadRequestMeeting() throws Exception{

		mockMvc.perform(put("/api/buildings/"+BUILDING_ID_1+"/meeting/"+ID_MEETING_NOT_FOUND+"/deactive").header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
	}
*/

}
