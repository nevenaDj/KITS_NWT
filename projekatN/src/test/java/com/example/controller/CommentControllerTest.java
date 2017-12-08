package com.example.controller;

import static com.example.constants.UserConstants.PASSWORD;
import static com.example.constants.UserConstants.USERNAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.example.constants.GlitchConstants.NEW_COMMENT;
import static com.example.constants.GlitchConstants.ID;
import static com.example.constants.GlitchConstants.ID_NOT_FOUND;

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
import com.example.dto.CommentDTO;
import com.example.dto.LoginDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class CommentControllerTest {

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
	public void loginTenant() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/login",
				new LoginDTO(USERNAME, PASSWORD), String.class);
		accessToken = responseEntity.getBody();
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddComment() throws Exception{
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setText(NEW_COMMENT);
		
		String json = TestUtils.convertObjectToJson(commentDTO);
		
		mockMvc.perform(post("/api/glitches/"+ ID +"/comments").header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isCreated());
		
	}
	
	@Test
	public void testAddCommentBadRequest() throws Exception{
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setText(NEW_COMMENT);
		
		String json = TestUtils.convertObjectToJson(commentDTO);
		
		mockMvc.perform(post("/api/glitches/"+ ID_NOT_FOUND +"/comments").header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isBadRequest());
		
	}
}
