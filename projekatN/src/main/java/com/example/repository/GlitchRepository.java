package com.example.repository;

import java.util.List;

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

	@Query(value = "SELECT g FROM Glitch g WHERE g.responsiblePerson.id = ?1",
			  countQuery = "SELECT count(*) FROM Glitch g WHERE g.responsiblePerson.id = ?1",
			    nativeQuery = false)
	public Page<Glitch> findGlitchByResponsiblePerson(Long id, Pageable page);

	@Query(value = "SELECT g FROM Glitch g WHERE g.item.id IS NULL")
	public List<Glitch> findWithoutMeeting();
	
	@Query("SELECT g FROM Glitch g WHERE g.tenant.id = ?1")
	public List<Glitch> findGlitchesOfTenantAll(Long id);

}
