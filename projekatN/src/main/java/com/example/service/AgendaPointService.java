package com.example.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.AgendaPoint;
import com.example.repository.AgendaPointRepository;

@Service
public class AgendaPointService {
	@Autowired
	AgendaPointRepository agendaPointRepository;

	public AgendaPoint save(AgendaPoint agendaPoint) {
		return agendaPointRepository.save(agendaPoint);
	}

	public AgendaPoint findOne(Long id) {
		return agendaPointRepository.findOne(id);
	}

	public Collection<AgendaPoint> findAllAgendaPoints(Long id) {
		return agendaPointRepository.findAllAgendaPoints(id);
	}

}
