package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Answer;
import com.example.repository.AnswerRepository;

@Service
public class AnswerService {

	@Autowired
	AnswerRepository answerRepository;

	public Answer save(Answer answer) {
		return answerRepository.save(answer);
	}

}
