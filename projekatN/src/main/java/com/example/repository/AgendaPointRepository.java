package com.example.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.AgendaPoint;

public interface AgendaPointRepository extends JpaRepository<AgendaPoint, Long> {
	@Query("SELECT p FROM AgendaPoint p WHERE p.meeting.id = ?1 ")
	public Collection<AgendaPoint> findAllAgendaPoints(Long id);

}
