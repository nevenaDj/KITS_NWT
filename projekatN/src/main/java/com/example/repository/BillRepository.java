package com.example.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Bill;
import com.example.model.Notification;

public interface BillRepository extends JpaRepository<Bill, Long>  {

	@Query(value = "SELECT b FROM Bill b WHERE b.glitch.id = ?1", nativeQuery = false)
	public Bill findByGlitch(Long glitchID);

	@Query(value = "SELECT b FROM Bill b WHERE b.date>?1 AND  b.date<?2", nativeQuery = false)
	public Bill findByDate(Date start, Date end);
		
	

}
