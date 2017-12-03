package com.example.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
	@Query("SELECT s FROM Survey s WHERE s.meeting.id = ?1 ")
	public Collection<Survey> findAllSurveys(Long id);

}
