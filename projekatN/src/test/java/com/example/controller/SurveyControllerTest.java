package com.example.controller;

import static com.example.constants.UserConstants.PASSWORD_PRESIDENT;
import static com.example.constants.UserConstants.USERNAME_PRESIDENT;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.example.constants.SurveyConstatnts.ID_MEETING;
import static com.example.constants.SurveyConstatnts.NEW_TITLE;
import static com.example.constants.SurveyConstatnts.NEW_END;
import static com.example.constants.SurveyConstatnts.NEW_OPTION_TEXT_1;
import static com.example.constants.SurveyConstatnts.NEW_OPTION_TEXT_2;
import static com.example.constants.SurveyConstatnts.NEW_QUESTION_TEXT;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

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
import com.example.dto.OptionDTO;
import com.example.dto.QuestionDTO;
import com.example.dto.SurveyDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SurveyControllerTest {
	
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
	public void testAddSurvey() throws Exception{
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(NEW_END);
		
		HashSet<OptionDTO> options = new HashSet<OptionDTO>();
		
		OptionDTO optionDTO1 = new OptionDTO(NEW_OPTION_TEXT_1);
		options.add(optionDTO1);
		OptionDTO optionDTO2 = new OptionDTO(NEW_OPTION_TEXT_2);
		options.add(optionDTO2);
		
		HashSet<QuestionDTO> questions = new HashSet<QuestionDTO>();
		
		QuestionDTO questionDTO = new QuestionDTO(NEW_QUESTION_TEXT, options);
		questions.add(questionDTO);
		
		
		SurveyDTO surveyDTO = new SurveyDTO(NEW_TITLE, endDate, null, questions);
		
		String json = TestUtils.convertObjectToJson(surveyDTO);
		
		mockMvc.perform(post("/meetings/"+ ID_MEETING +  "/surveys")
				.header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.title").value(NEW_TITLE))
		.andExpect(jsonPath("$.questions.[*].text").value(hasItem(NEW_QUESTION_TEXT)))
		.andExpect(jsonPath("$.questions.[*].options.[*].text").value(hasItem(NEW_OPTION_TEXT_1)))
		.andExpect(jsonPath("$.questions.[*].options.[*].text").value(hasItem(NEW_OPTION_TEXT_2)));
	}

}
