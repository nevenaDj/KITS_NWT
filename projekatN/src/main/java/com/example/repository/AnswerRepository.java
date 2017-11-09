package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
