package com.example.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.AgendaItem;
import com.example.repository.AgendaItemRepository;

@Service
public class AgendaItemService {
	@Autowired
	AgendaItemRepository agendaPointRepository;

	public AgendaItem save(AgendaItem agendaPoint) {
		return agendaPointRepository.save(agendaPoint);
	}

	public AgendaItem findOne(Long id) {
		return agendaPointRepository.findOne(id);
	}

	public Collection<AgendaItem> findAllAgendaPoints(Long id) {
		return agendaPointRepository.findAllAgendaItems(id);
	}
	
	public void delete(Long id) {
		agendaPointRepository.delete(id);
	}

}
