package com.example.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<CommunalProblem> findProblemsByBuilding(Long id, Pageable page) {
		// TODO Auto-generated method stub
		return communalProblemRepository.findProblemsByBuilding( id, page);
	}

	public List<CommunalProblem> findProblemsByBuilding(Long id) {
		// TODO Auto-generated method stub
		return communalProblemRepository.findProblemsByBuilding(id);
	}

	public Integer findProblemsByBuildingCount(Long id) {
		// TODO Auto-generated method stub
		return communalProblemRepository.findProblemsByBuildingCount(id);
	}

	public Page<CommunalProblem> findActiveProblemsByBuilding(Long id, Pageable page) {
		// TODO Auto-generated method stub
		return communalProblemRepository.findActiveProblemsByBuilding( id,new Date() ,page);
	}
	
	public Integer findActiveProblemsByBuildingCount(Long id) {
		// TODO Auto-generated method stub
		return communalProblemRepository.findActiveProblemsByBuildingCount(id, new Date());
	}
}
