package com.example.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.CommunalProblem;

public interface CommunalProblemRepository extends JpaRepository<CommunalProblem, Long> {

	@Query(value = "SELECT p FROM CommunalProblem p WHERE p.item.id IS NULL AND p.building.id=?1")
	List<CommunalProblem> findWithoutMeeting(Long id);

	@Query(value = "SELECT p FROM CommunalProblem p WHERE p.building.id=?1", 
			countQuery= "SELECT count(*) FROM CommunalProblem p WHERE  p.building.id=?1",
			nativeQuery=false )
	Page<CommunalProblem> findProblemsByBuilding(Long id, Pageable page);

	@Query(value = "SELECT count(*) FROM CommunalProblem p WHERE  p.building.id=?1",
			   nativeQuery = false)
		public Integer findProblemsByBuildingCount(Long id);

	@Query(value = "SELECT p FROM CommunalProblem p WHERE p.building.id=?1 AND p.dateOfRepair<?2", 
			countQuery= "SELECT count(*) FROM CommunalProblem p WHERE  p.building.id=?1 AND p.dateOfRepair<?2",
			nativeQuery=false )
	Page<CommunalProblem> findActiveProblemsByBuilding(Long id, Date date, Pageable page);

	@Query(value =  "SELECT count(*) FROM CommunalProblem p WHERE  p.building.id=?1 AND p.dateOfRepair<?2",
			nativeQuery=false )
	Integer findActiveProblemsByBuildingCount(Long id, Date date);
	
	@Query(value = "SELECT p FROM CommunalProblem p WHERE p.building.id=?1", 
			nativeQuery=false )
	List<CommunalProblem> findProblemsByBuilding(Long id);

	
}
