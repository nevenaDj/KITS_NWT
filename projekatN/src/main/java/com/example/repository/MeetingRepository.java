package com.example.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Bill;
import com.example.model.Building;
import com.example.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
	
	@Query(value = "SELECT m FROM Meeting m WHERE m.building.id=?1 AND m.dateAndTime=?2", nativeQuery = false)
	public Meeting findByDate(Long building_id, Date date);

	@Query(value = "SELECT m.dateAndTime FROM Meeting m WHERE m.dateAndTime=?1", nativeQuery = false)
	public ArrayList<Date> getDates(Long id);

}
