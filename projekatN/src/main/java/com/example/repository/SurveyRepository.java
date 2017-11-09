package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
