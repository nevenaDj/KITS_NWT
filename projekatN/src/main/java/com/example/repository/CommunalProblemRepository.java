package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.CommunalProblem;

public interface CommunalProblemRepository extends JpaRepository<CommunalProblem, Long> {

	@Query(value = "SELECT p FROM CommunalProblem p WHERE p.item.id IS NULL")
	List<CommunalProblem> findWithoutMeeting();

}
