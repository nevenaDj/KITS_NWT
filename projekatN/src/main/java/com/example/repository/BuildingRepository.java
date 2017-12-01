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

	@Query("SELECT b FROM Building b, Address a WHERE b.address.id = a.id AND a.street = ?1 AND a.number = ?2 AND a.city = ?3")
	Building findByAddress(String street, String number, String city);

}
