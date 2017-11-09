package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.SurveyType;

public interface SurveyTypeRepository extends JpaRepository<SurveyType, Long> {

}
