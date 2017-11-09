package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OptionDTO;
import com.example.dto.QuestionDTO;
import com.example.dto.SurveyDTO;
import com.example.model.Meeting;
import com.example.model.Option;
import com.example.model.Question;
import com.example.model.Survey;
import com.example.service.MeetingService;
import com.example.service.OptionService;
import com.example.service.QuestionService;
import com.example.service.SurveyService;

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

	@RequestMapping(value = "/meetings/{id}/surveys", method = RequestMethod.POST, consumes = "application/json")
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

}
