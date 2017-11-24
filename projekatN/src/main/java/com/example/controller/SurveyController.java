package com.example.controller;

import java.util.List;

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

@RestController
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
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<SurveyDTO> addSurvey(@PathVariable Long id, @RequestBody SurveyDTO surveyDTO) {
		Survey survey = SurveyDTO.getSurvey(surveyDTO);

		Meeting meeting = meetingService.findOne(id);
		if (meeting == null) {
			return new ResponseEntity<SurveyDTO>(HttpStatus.BAD_REQUEST);
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

		return new ResponseEntity<SurveyDTO>(surveyDTO, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/surveys/{id}/answers", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_OWNER')")
	public ResponseEntity<List<AnswerDTO>> addAnswers(@PathVariable Long id, @RequestBody List<AnswerDTO> answersDTO,
			HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User owner = userService.findByUsername(username);

		Survey survey = surveyService.findOne(id);

		if (survey == null) {
			return new ResponseEntity<List<AnswerDTO>>(HttpStatus.BAD_REQUEST);
		}

		for (AnswerDTO answerDTO : answersDTO) {
			Question question = questionService.findOne(answerDTO.getQuestionID());
			if (question == null) {
				return new ResponseEntity<List<AnswerDTO>>(HttpStatus.BAD_REQUEST);
			}

			for (Long optionID : answerDTO.getOptions()) {
				Option option = optionService.findOne(optionID);
				if (option == null) {
					return new ResponseEntity<List<AnswerDTO>>(HttpStatus.BAD_REQUEST);
				}

				Answer answer = new Answer(survey, question, option, owner);
				answerService.save(answer);
			}
		}

		return new ResponseEntity<List<AnswerDTO>>(HttpStatus.OK);
	}

}
