package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Glitch;

public interface GlitchRepository extends JpaRepository<Glitch, Long> {

	@Query("SELECT g FROM Glitch g WHERE g.tenant.id = ?1")
	public Page<Glitch> findGlitchesOfTenant(Long id, Pageable page);

	@Query("SELECT g FROM Glitch g WHERE g.company.id = ?1")
	public Page<Glitch> findGlitchesOfCompany(Long id, Pageable page);

}
