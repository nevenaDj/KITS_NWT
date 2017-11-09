package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Question;
import com.example.repository.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	QuestionRepository questionRepository;

	public Question save(Question question) {
		return questionRepository.save(question);
	}
}
