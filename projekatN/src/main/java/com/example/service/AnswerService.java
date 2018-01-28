package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Answer;
import com.example.model.Survey;
import com.example.model.User;
import com.example.repository.AnswerRepository;

@Service
public class AnswerService {

	@Autowired
	AnswerRepository answerRepository;

	public Answer save(Answer answer) {
		return answerRepository.save(answer);
	}
	
	public Boolean hasAnswer(Survey survey, User user){
		List<Answer> answers = answerRepository.findAnswerForOwner(survey.getId(), user.getId());
		return !answers.isEmpty();
	}

}
