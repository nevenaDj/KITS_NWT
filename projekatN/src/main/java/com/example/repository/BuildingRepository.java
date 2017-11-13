package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Building;
import com.example.model.User;

public interface BuildingRepository extends JpaRepository<Building, Long> {

	@Modifying
	@Transactional
	@Query("UPDATE Building b SET b.president = ?2 WHERE b.id = ?1")
	void setPresident(Long id, User user);

}
