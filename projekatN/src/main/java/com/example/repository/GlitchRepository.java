package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Building;
import com.example.model.Glitch;
import com.example.model.User;

public interface GlitchRepository extends JpaRepository<Glitch, Long> {

	@Query("SELECT g FROM Glitch g WHERE g.tenant.id = ?1 AND g.apartment.id = ?2")
	public Page<Glitch> findGlitchesOfTenant(Long id, Long idApartment, Pageable page);

	@Query("SELECT g FROM Glitch g WHERE g.company.id = ?1")
	public Page<Glitch> findGlitchesOfCompany(Long id, Pageable page);

	@Query(value = "SELECT g FROM Glitch g WHERE g.responsiblePerson.id = ?1",
			  countQuery = "SELECT count(*) FROM Glitch g WHERE g.responsiblePerson.id = ?1",
			    nativeQuery = false)
	public Page<Glitch> findGlitchByResponsiblePerson(Long id, Pageable page);

	@Query(value = "SELECT g FROM Glitch g WHERE g.item.id IS NULL AND g.apartment.building.id=?1")
	public List<Glitch> findWithoutMeeting(Long id);
	
	@Query("SELECT g FROM Glitch g WHERE g.tenant.id = ?1 AND g.apartment.id = ?2")
	public List<Glitch> findGlitchesOfTenantAll(Long id, Long idApartment);

	@Query(value = "SELECT g FROM Glitch g WHERE g.company.id=?1 AND g.state.id=2")
	public List<Glitch> findActiveGlitches(Long id);
	
	@Query(value = "SELECT g FROM Glitch g WHERE g.company.id=?1 AND g.state.id=1")
	public List<Glitch> findPendingGlitches(Long id);

	@Query(value = "SELECT count(g) FROM Glitch g WHERE g.responsiblePerson.id = ?1")
	public Integer findMyResponsibilitiesCount(Long id);

	@Query(value = "SELECT g.apartment.building FROM  Glitch g WHERE g.id = ?1")
	public Building findBuilding(Long id);

	@Query(value = "SELECT au.tenant FROM UserAparment au WHERE au.apartment.building.id = ?1")
	public List<User> findUserByBuilding(Long id);


}
