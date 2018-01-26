package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.CommunalProblem;
import com.example.repository.CommunalProblemRepository;

@Service
public class CommunalProblemService {
	@Autowired
	CommunalProblemRepository communalProblemRepository;

	public CommunalProblem save(CommunalProblem communalProblem) {
		return communalProblemRepository.save(communalProblem);
	}

	public CommunalProblem findOne(Long id) {
		return communalProblemRepository.findOne(id);
	}

	public List<CommunalProblem> findWithoutMeeting(Long id) {
		return communalProblemRepository.findWithoutMeeting(id);
	}
}
