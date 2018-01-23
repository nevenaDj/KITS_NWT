package com.example.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Bill;


public interface BillRepository extends JpaRepository<Bill, Long>  {

	@Query(value = "SELECT g.bill FROM Glitch g WHERE g.id = ?1", nativeQuery = false)
	public Bill findByGlitch(Long glitchID);

	@Query(value = "SELECT b FROM Bill b WHERE b.date>?1 AND  b.date<?2", nativeQuery = false)
	public Bill findByDate(Date start, Date end);

	@Query(value = "SELECT b FROM Bill b WHERE b.company.id = ?1",
			  countQuery = "SELECT count(*) FROM Bill b WHERE b.company.id = ?1",
			    nativeQuery = false)
	public Page<Bill> findAllByCompnany(Long userId, Pageable pageable);

	@Query(value = "SELECT count(b) FROM Bill b WHERE b.company.id = ?1")
	public Long countByCompany(Long id);

	@Query(value = "SELECT b FROM Bill b WHERE b.glitch.apartment.building.president.id  = ?1",
			  countQuery = "SELECT count(*) FROM Bill b WHERE b.glitch.apartment.building.president.id = ?1",
			    nativeQuery = false)
	public Page<Bill> findAllByPresident(Long id, Pageable page);

	@Query(value = "SELECT count(b) FROM Bill b WHERE b.glitch.apartment.building.president.id  = ?1")
	public Long countByPresident(Long id);
		
	

}
