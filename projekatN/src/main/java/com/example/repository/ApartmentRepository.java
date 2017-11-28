package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
	@Query("SELECT a FROM Apartment a WHERE a.building.id = ?1 ")
	public Page<Apartment> findApartments(Long idBuilding, Pageable page);

}
