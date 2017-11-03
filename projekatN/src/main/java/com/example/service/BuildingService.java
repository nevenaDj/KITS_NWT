package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Building;
import com.example.repository.BuildingRepository;

@Service
public class BuildingService {

	@Autowired
	BuildingRepository buildingRepository;

	public Building findOne(Long id) {
		return buildingRepository.findOne(id);
	}

	public Page<Building> findAll(Pageable page) {
		return buildingRepository.findAll(page);
	}

	public Building save(Building building) {
		return buildingRepository.save(building);
	}

	public void remove(Long id) {
		buildingRepository.delete(id);
	}

}
