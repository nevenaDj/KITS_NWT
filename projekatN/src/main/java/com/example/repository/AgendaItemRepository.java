package com.example.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.AgendaItem;

public interface AgendaItemRepository extends JpaRepository<AgendaItem, Long> {
	@Query("SELECT i FROM AgendaItem i WHERE i.meeting.id = ?1 ")
	public Collection<AgendaItem> findAllAgendaItems(Long id);

}
