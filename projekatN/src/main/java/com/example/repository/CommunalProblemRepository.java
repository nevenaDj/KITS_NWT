package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.CommunalProblem;

public interface CommunalProblemRepository extends JpaRepository<CommunalProblem, Long> {

}
