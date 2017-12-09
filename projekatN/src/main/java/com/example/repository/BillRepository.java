package com.example.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Bill;


public interface BillRepository extends JpaRepository<Bill, Long>  {

	@Query(value = "SELECT g.bill FROM Glitch g WHERE g.id = ?1", nativeQuery = false)
	public Bill findByGlitch(Long glitchID);

	@Query(value = "SELECT b FROM Bill b WHERE b.date>?1 AND  b.date<?2", nativeQuery = false)
	public Bill findByDate(Date start, Date end);
		
	

}
