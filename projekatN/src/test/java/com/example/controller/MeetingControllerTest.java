package com.example.controller;

import static com.example.constants.UserConstants.PASSWORD_PRESIDENT;
import static com.example.constants.UserConstants.USERNAME_PRESIDENT;
import static com.example.constants.BuildingConstatnts.BUILDING_ID_1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class MeetingControllerTest {
	
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
	}
	
	@Before
	public void loginPresident() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/login",
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
		
		mockMvc.perform(post("/buildings/" + BUILDING_ID_1 + "/meetings").header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isCreated());
	}
	

}
