package com.example.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Answer;
import com.example.model.Survey;
import com.example.repository.AnswerRepository;
import com.example.repository.SurveyRepository;

@Service
public class SurveyService {

	@Autowired
	SurveyRepository surveyRepsotory;
	@Autowired
	AnswerRepository answerRepository;

	public Survey save(Survey survey) {
		return surveyRepsotory.save(survey);
	}

	public Survey findOne(Long id) {
		return surveyRepsotory.findOne(id);
	}

	public Collection<Survey> findAllSurveys(Long id) {
		return surveyRepsotory.findAllSurveys(id);
	}
	
	public boolean remove(Long id){
		List<Answer> answers = answerRepository.findAllAnswers(id);
		if (answers.isEmpty()){
			surveyRepsotory.delete(id);
			return true;
		}
		return false;
		
		
	}
}
