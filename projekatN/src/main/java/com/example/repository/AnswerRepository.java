package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	@Query("SELECT a FROM Answer a WHERE a.survey.id = ?1 ")
	public List<Answer> findAllAnswers(Long id);

}
