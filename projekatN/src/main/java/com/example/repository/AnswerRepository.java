package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	@Query("SELECT a FROM Answer a WHERE a.survey.id = ?1 ")
	public List<Answer> findAllAnswers(Long id);
	
	@Query("SELECT a FROM Answer a WHERE a.survey.id = ?1 AND a.user.id = ?2 ")
	public List<Answer> findAnswerForOwner(Long idSurvey, Long idOwner);
	
	@Query("SELECT count(*) FROM Answer a WHERE a.survey.id = ?1 AND a.user.id = ?2 AND a.question.id = ?3 AND a.option.id = ?4")
	public int getAnswer(Long idSurvey, Long idUser, Long idQuestion, Long idOption);

}
