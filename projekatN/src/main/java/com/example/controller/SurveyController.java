package com.example.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AnswerDTO;
import com.example.dto.OptionDTO;
import com.example.dto.QuestionDTO;
import com.example.dto.SurveyDTO;
import com.example.model.Answer;
import com.example.model.Meeting;
import com.example.model.Option;
import com.example.model.Question;
import com.example.model.Survey;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.AnswerService;
import com.example.service.MeetingService;
import com.example.service.OptionService;
import com.example.service.QuestionService;
import com.example.service.SurveyService;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
@Api(value = "surveys")
public class SurveyController {
	@Autowired
	MeetingService meetingService;
	@Autowired
	SurveyService surveyService;
	@Autowired
	QuestionService questionService;
	@Autowired
	OptionService optionService;
	@Autowired
	AnswerService answerService;
	@Autowired
	TokenUtils tokenUtils;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/meetings/{id}/surveys", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Create a survey.", notes = "Returns the survey being saved.", httpMethod = "POST", 
				produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = SurveyDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	/*** add new survey ***/
	public ResponseEntity<SurveyDTO> addSurvey(
			@ApiParam(value = "The ID of the meeting.", required = true) @PathVariable Long id,
			@ApiParam(value = "The surveyDTO object", required = true) @RequestBody SurveyDTO surveyDTO) {
		Survey survey = SurveyDTO.getSurvey(surveyDTO);

		Meeting meeting = meetingService.findOne(id);
		if (meeting == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		survey.setMeeting(meeting);
		survey = surveyService.save(survey);
		surveyDTO.setId(survey.getId());

		for (QuestionDTO questionDTO : surveyDTO.getQuestions()) {
			Question question = QuestionDTO.getQuestion(questionDTO, survey);

			question = questionService.save(question);
			questionDTO.setId(question.getId());

			for (OptionDTO optionDTO : questionDTO.getOptions()) {
				Option option = OptionDTO.getOption(optionDTO, question);
				option = optionService.save(option);
				optionDTO.setId(option.getId());
			}

		}

		return new ResponseEntity<>(surveyDTO, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/surveys/{id}/answers", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Create an answer.", httpMethod = "POST", 
	produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasRole('ROLE_OWNER')")
	/*** add answer to the survey ***/
	public ResponseEntity<List<AnswerDTO>> addAnswers(
			@ApiParam(value = "The ID of the survey.", required = true) @PathVariable Long id, 
			@ApiParam(value = "The list of answerDTOs objects", required = true) @RequestBody List<AnswerDTO> answersDTO,
			HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User owner = userService.findByUsername(username);

		Survey survey = surveyService.findOne(id);

		if (survey == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		for (AnswerDTO answerDTO : answersDTO) {
			Question question = questionService.findOne(answerDTO.getQuestionID());
			if (question == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			for (Long optionID : answerDTO.getOptions()) {
				Option option = optionService.findOne(optionID);
				if (option == null) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}

				Answer answer = new Answer(survey, question, option, owner);
				answerService.save(answer);
			}
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/surveys/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get a survey.", httpMethod = "GET")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = SurveyDTO.class),
			@ApiResponse(code = 404, message = "Not found") })
	/*** get survey by id ***/
	public ResponseEntity<SurveyDTO> getSurvey(
			@ApiParam(value = "The ID of the survey.", required = true) @PathVariable Long id){
		Survey survey = surveyService.findOne(id);
		
		if (survey == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Set<QuestionDTO> questions = new HashSet<>();
		
		for (Question question : survey.getQuestions()) {
			Set<OptionDTO> options = new HashSet<>();
			for (Option option : question.getOptions()) {
				options.add(new OptionDTO(option));
			}
			questions.add(new QuestionDTO(question,options));
		}

		return new ResponseEntity<>(new SurveyDTO(survey, questions), HttpStatus.OK);
		
	}

}
