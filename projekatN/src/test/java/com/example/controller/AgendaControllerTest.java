package com.example.controller;

import static com.example.constants.AgendaItemConstants.*;
import static com.example.constants.AgendaItemConstants.NEW_NUMBER;
import static com.example.constants.UserConstants.*;
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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import com.example.dto.AgendaDTO;
import com.example.dto.AgendaItemDTO;
import com.example.dto.ApartmentDTO;
import com.example.dto.ItemCommentDTO;
import com.example.dto.LoginDTO;
import com.example.dto.UserDTO;
import com.example.model.ItemType;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class AgendaControllerTest {
	
	private String accessTokenOwner;
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
	public void testAddAgendaItem() throws Exception{
		AgendaItemDTO itemDTO = new AgendaItemDTO(ID_ITEM, NEW_NUMBER,NEW_TYPE, NEW_TITLE);
	
		String json = TestUtils.convertObjectToJson(itemDTO);
		
		mockMvc.perform(post("/api/meetings/" + ID_MEETING + "/items")
							.header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.number").value(NEW_NUMBER))
		.andExpect(jsonPath("$.title").value(NEW_TITLE));
	}
	
	@Test
	public void testAddAgendaItemBadRequest() throws Exception{
		AgendaItemDTO itemDTO = new AgendaItemDTO(ID_ITEM, NEW_NUMBER,NEW_TYPE, NEW_TITLE);
		
		String json = TestUtils.convertObjectToJson(itemDTO);
		
		mockMvc.perform(post("/api/meetings/" + ID_MEETING_NOT_FOUND + "/items")
							.header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testGetAgendaItem() throws Exception{
		mockMvc.perform(get("/api/meetings/"+ID_MEETING+"/items/"+ID.intValue()).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.title").value(TITLE))
		.andExpect(jsonPath("$.number").value(NUMBER));
		
	}
	
	@Test
	public void testGetAgendaItemNotFound() throws Exception{
		mockMvc.perform(get("/api/meetings/"+ID_MEETING+"/items/"+ID_ITEM_NOT_FOUND.intValue()).header("X-Auth-Token", accessToken))
		.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void testGetAgendaItemBadRequest() throws Exception{
		mockMvc.perform(get("/api/meetings/"+ID_MEETING_NOT_FOUND+"/items/"+ID.intValue()).header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
		
	}
	
	
	//?????
	@Test
	public void testGetAgenda() throws Exception{
		mockMvc.perform(get("/api/agendas/"+ID_MEETING).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType));
	//	.andExpect(jsonPath("$.[*].id").value(hasItem(ID.intValue())))
		///.andExpect(jsonPath("$.[*].title").value(hasItem(TITLE)))
	//	.andExpect(jsonPath("$.[*].number").value(hasItem(NUMBER)));
		
	}
	
	//?????
	@Test
	public void testGetAgendaWithoutMeeting() throws Exception{
		mockMvc.perform(get("/api/agendas/no_meeting").header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteAgendaItem() throws Exception{
		mockMvc.perform(delete("/api/meetings/"+ID_MEETING+"/items/"+ID.intValue()).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteAgendaItemNotFound() throws Exception{
		mockMvc.perform(delete("/api/meetings/"+ID_MEETING+"/items/"+ID_ITEM_NOT_FOUND.intValue()).header("X-Auth-Token", accessToken))
		.andExpect(status().isNotFound());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteAgendaItemBadRequest() throws Exception{
		mockMvc.perform(delete("/api/meetings/"+ID_MEETING_NOT_FOUND+"/items/"+ID.intValue()).header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateAgendaItem() throws Exception{
		AgendaItemDTO itemDTO = new AgendaItemDTO(ID, NEW_NUMBER,NEW_TYPE, NEW_TITLE);
		
		String json = TestUtils.convertObjectToJson(itemDTO);
		
		mockMvc.perform(put("/api/meetings/"+ID_MEETING+"/items/"+ID).header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.number").value(NEW_NUMBER))
		.andExpect(jsonPath("$.title").value(NEW_TITLE));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateAgendaItemBadRequest() throws Exception{
		AgendaItemDTO itemDTO = new AgendaItemDTO(ID, NEW_NUMBER,NEW_TYPE, NEW_TITLE);
		
		String json = TestUtils.convertObjectToJson(itemDTO);
		
		mockMvc.perform(put("/api/meetings/"+ID_MEETING_NOT_FOUND+"/items/"+ID).header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateAgendaItemNotFound() throws Exception{
		AgendaItemDTO itemDTO = new AgendaItemDTO(ID, NEW_NUMBER,NEW_TYPE, NEW_TITLE);
		
		String json = TestUtils.convertObjectToJson(itemDTO);
		
		mockMvc.perform(put("/api/meetings/"+ID_MEETING+"/items/"+ID_ITEM_NOT_FOUND).header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isNotFound());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateConclusionAgenda() throws Exception{
		AgendaItemDTO itemDTO = new AgendaItemDTO(ID, NEW_NUMBER,NEW_TYPE, NEW_TITLE);
		itemDTO.setConclusion(NEW_CONCLUSION);
		
		String json = TestUtils.convertObjectToJson(itemDTO);

		
		mockMvc.perform(put("/api/meetings/"+ID_MEETING+"/items/"+ID).header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.conclusion").value(NEW_CONCLUSION));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateConclusionAgendaBadRequest() throws Exception{
		AgendaItemDTO itemDTO = new AgendaItemDTO(ID, NEW_NUMBER,NEW_TYPE, NEW_TITLE);
		itemDTO.setConclusion(NEW_CONCLUSION);
		
		String json = TestUtils.convertObjectToJson(itemDTO);
		
		mockMvc.perform(put("/api/meetings/"+ID_MEETING_NOT_FOUND+"/items/"+ID).header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateConclusionAgendaNotFound() throws Exception{
		AgendaItemDTO itemDTO = new AgendaItemDTO(ID, NEW_NUMBER,NEW_TYPE, NEW_TITLE);
		itemDTO.setConclusion(NEW_CONCLUSION);
		
		String json = TestUtils.convertObjectToJson(itemDTO);
		
		mockMvc.perform(put("/api/meetings/"+ID_MEETING+"/items/"+ID_ITEM_NOT_FOUND).header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isNotFound());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void updateAgendaItemNumber() throws Exception{
		AgendaItemDTO itemDTO = new AgendaItemDTO(ID, NEW_NUMBER,NEW_TYPE, NEW_TITLE);
		AgendaDTO agendaDTO = new AgendaDTO();
		Set<AgendaItemDTO> set= new HashSet<AgendaItemDTO>();
		set.add(itemDTO);
		agendaDTO.setAgendaPoints(set);
		
		String json = TestUtils.convertObjectToJson(agendaDTO);

		
		mockMvc.perform(put("/api/agendas/"+ID).header("X-Auth-Token", accessToken)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.[*].number").value(hasItem(NEW_NUMBER)));
		
	}
	
	
	@Before
	public void loginOwner() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/login",
				new LoginDTO(USERNAME_OWNER, PASSWORD_OWNER), String.class);
		accessTokenOwner = responseEntity.getBody();
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testAddComment() throws Exception{
		UserDTO userDTO= new UserDTO();
		ItemCommentDTO commentDTO = new ItemCommentDTO(ID_COMMENT, userDTO, NEW_TEXT, new Date());
		
		String json = TestUtils.convertObjectToJson(commentDTO);
		
		mockMvc.perform(post("/api/meetings/" + ID_MEETING + "/items/"+ID+"/comments")
							.header("X-Auth-Token", accessTokenOwner)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(contentType));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddCommentBadRequest() throws Exception{
		UserDTO userDTO= new UserDTO();
		ItemCommentDTO commentDTO = new ItemCommentDTO(ID_COMMENT, userDTO, NEW_TEXT, new Date());
		
		String json = TestUtils.convertObjectToJson(commentDTO);
		
		mockMvc.perform(post("/api/meetings/" + ID_MEETING_NOT_FOUND + "/items/"+ID+"/comments")
							.header("X-Auth-Token", accessTokenOwner)
							.contentType(contentType)
							.content(json))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testGetComments() throws Exception{
		mockMvc.perform(get("/api/meetings/"+ID_MEETING+"/items/"+ID.intValue()+"/comments").header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[*].text").value(hasItem(TEXT)));
		
	}
	
	@Test
	public void testGetCommentsBadRequest() throws Exception{
		mockMvc.perform(get("/api/meetings/"+ID_MEETING+"/items/"+ID_ITEM_NOT_FOUND.intValue()+"/comments").header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteComment() throws Exception{
		mockMvc.perform(delete("/api/meetings/"+ID_MEETING+"/items/"+ID.intValue()+"/comments/"+ID_COMMENT)
				.header("X-Auth-Token", accessToken))
				.andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteCommentBadRequest() throws Exception{
		mockMvc.perform(delete("/api/meetings/"+ID_MEETING_NOT_FOUND+"/items/"+ID.intValue()+"/comments/"+ID_COMMENT)
				.header("X-Auth-Token", accessToken))
				.andExpect(status().isBadRequest());
	}


}
