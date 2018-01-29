package com.example.controller;

import static com.example.constants.UserConstants.PASSWORD_PRESIDENT;
import static com.example.constants.UserConstants.USERNAME_PRESIDENT;
import static com.example.constants.UserConstants.USERNAME_OWNER;
import static com.example.constants.UserConstants.PASSWORD_OWNER;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import static com.example.constants.SurveyConstatnts.ID_MEETING_NOT_FOUND;
import static com.example.constants.SurveyConstatnts.ID_QUESTION_1;
import static com.example.constants.SurveyConstatnts.ID_QUESTION_2;
import static com.example.constants.SurveyConstatnts.ID_OPTION_1_1;
import static com.example.constants.SurveyConstatnts.ID_OPTION_1_2;
import static com.example.constants.SurveyConstatnts.ID_OPTION_2_1;
import static com.example.constants.SurveyConstatnts.ID_SURVEY;
import static com.example.constants.SurveyConstatnts.ID_SURVEY_NOT_FOUND;
import static com.example.constants.SurveyConstatnts.ID_QUESTION_NOT_FOUND;
import static com.example.constants.SurveyConstatnts.ID_OPTION_NOT_FOUND;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import com.example.dto.AnswerDTO;
import com.example.dto.LoginDTO;
import com.example.dto.OptionDTO;
import com.example.dto.QuestionDTO;
import com.example.dto.SurveyDTO;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class SurveyControllerTest {
	
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
		
		mockMvc.perform(post("/api/meetings/"+ ID_MEETING +  "/surveys")
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

	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddSurveyBadRequest() throws Exception{
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
		
		mockMvc.perform(post("/api/meetings/"+ ID_MEETING_NOT_FOUND +  "/surveys")
				.header("X-Auth-Token", accessToken)
				.contentType(contentType)
				.content(json))
		.andExpect(status().isBadRequest());
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
	public void testAddAnswer() throws Exception{
		List<AnswerDTO> answers = new ArrayList<>();
		Set<Long> options1 = new HashSet<>();
		options1.add(ID_OPTION_1_1);
		options1.add(ID_OPTION_1_2);
		answers.add(new AnswerDTO(ID_QUESTION_1, options1));
		Set<Long> options2 = new HashSet<>();
		options2.add(ID_OPTION_2_1);
		answers.add(new AnswerDTO(ID_QUESTION_2, options2));
		
		String json = TestUtils.convertObjectToJson(answers);
		
		mockMvc.perform(post("/api/surveys/" + ID_SURVEY + "/answers").header("X-Auth-Token", accessTokenOwner)
				.contentType(contentType)
				.content(json)).andExpect(status().isCreated());
		
	}
	
	@Test
	public void testAddAnswerBadSurveyID() throws Exception{
		List<AnswerDTO> answers = new ArrayList<>();
		Set<Long> options1 = new HashSet<>();
		options1.add(ID_OPTION_1_1);
		options1.add(ID_OPTION_1_2);
		answers.add(new AnswerDTO(ID_QUESTION_1, options1));
		Set<Long> options2 = new HashSet<>();
		options2.add(ID_OPTION_2_1);
		answers.add(new AnswerDTO(ID_QUESTION_2, options2));
		
		String json = TestUtils.convertObjectToJson(answers);
		
		mockMvc.perform(post("/api/surveys/" + ID_SURVEY_NOT_FOUND + "/answers").header("X-Auth-Token", accessTokenOwner)
				.contentType(contentType)
				.content(json)).andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void testAddAnswerBadQuestionID() throws Exception{
		List<AnswerDTO> answers = new ArrayList<>();
		Set<Long> options1 = new HashSet<>();
		options1.add(ID_OPTION_1_1);
		options1.add(ID_OPTION_1_2);
		answers.add(new AnswerDTO(ID_QUESTION_NOT_FOUND, options1));
		Set<Long> options2 = new HashSet<>();
		options2.add(ID_OPTION_2_1);
		answers.add(new AnswerDTO(ID_QUESTION_2, options2));
		
		String json = TestUtils.convertObjectToJson(answers);
		
		mockMvc.perform(post("/api/surveys/" + ID_SURVEY + "/answers").header("X-Auth-Token", accessTokenOwner)
				.contentType(contentType)
				.content(json)).andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void testAddAnswerBadOptionID() throws Exception{
		List<AnswerDTO> answers = new ArrayList<>();
		Set<Long> options1 = new HashSet<>();
		options1.add(ID_OPTION_NOT_FOUND);
		options1.add(ID_OPTION_1_2);
		answers.add(new AnswerDTO(ID_QUESTION_1, options1));
		Set<Long> options2 = new HashSet<>();
		options2.add(ID_OPTION_2_1);
		answers.add(new AnswerDTO(ID_QUESTION_2, options2));
		
		String json = TestUtils.convertObjectToJson(answers);
		
		mockMvc.perform(post("/api/surveys/" + ID_SURVEY + "/answers").header("X-Auth-Token", accessTokenOwner)
				.contentType(contentType)
				.content(json)).andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void testGetSurvey() throws Exception{
		mockMvc.perform(get("/api/surveys/" + ID_SURVEY).header("X-Auth-Token", accessToken))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(ID_SURVEY))
		.andExpect(jsonPath("$.questions.[*].id").value(hasItem(ID_QUESTION_1.intValue())))
		.andExpect(jsonPath("$.questions.[*].id").value(hasItem(ID_QUESTION_2.intValue())))
		.andExpect(jsonPath("$.questions.[*].options.[*].id").value(hasItem(ID_OPTION_1_1.intValue())));
	}
	
	@Test
	public void testGetSurveyNotFound() throws Exception{
		mockMvc.perform(get("/api/surveys/" + ID_SURVEY_NOT_FOUND).header("X-Auth-Token", accessToken))
		.andExpect(status().isNotFound());
	}
	@Test
	public void testGetAnswers() throws Exception{
		mockMvc.perform(get("/api/surveys/" + ID_SURVEY+"/answers").header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testGetAnswersNF() throws Exception{
		mockMvc.perform(get("/api/surveys/" + 2000000L+"/answers").header("X-Auth-Token", accessToken))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetHasAnswers() throws Exception{
		mockMvc.perform(get("/api/surveys/" + ID_SURVEY+"/hasAnswer").header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testGetHasAnswersNF() throws Exception{
		mockMvc.perform(get("/api/surveys/" + 2000000L+"/hasAnswer").header("X-Auth-Token", accessToken))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void testDeleteSurveys() throws Exception{
		mockMvc.perform(delete("/api/surveys/" + ID_SURVEY).header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testDeleteSurveysNF() throws Exception{
		mockMvc.perform(delete("/api/surveys/" + 2000000L).header("X-Auth-Token", accessToken))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetSurveysByMeeting() throws Exception{
		mockMvc.perform(get("/api/meetings/" + ID_SURVEY+"/surveys").header("X-Auth-Token", accessToken))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testGetSurveysByMeetingNF() throws Exception{
		mockMvc.perform(get("/api/meetings/" + 2000000L+"/surveys").header("X-Auth-Token", accessToken))
		.andExpect(status().isBadRequest());
	}
}
