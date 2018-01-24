package com.example.repository;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

	@Query(value = "SELECT m FROM Meeting m WHERE m.building.id=?1 AND m.dateAndTime=?2", nativeQuery = false)
	public Meeting findByDate(Long buildingId, Date date);

	@Query(value = "SELECT m.dateAndTime FROM Meeting m WHERE m.id=?1", nativeQuery = false)
	public List<Date> getDates(Long id);

	@Query(value = "SELECT m FROM Meeting m WHERE m.building.id=?1", 
		   countQuery = "SELECT count(*) FROM Meeting m WHERE m.building.id = ?1",
		   nativeQuery = false)
	public Page<Meeting> findByBuilding(Long id, org.springframework.data.domain.Pageable page);

	@Query(value = "SELECT count(*) FROM Meeting m WHERE m.building.id = ?1",
			   nativeQuery = false)
		public Integer findByBuildingCount(Long id);

}
