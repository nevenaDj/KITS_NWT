package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Building;
import com.example.repository.AddressRepository;
import com.example.repository.BuildingRepository;

@Service
public class BuildingService {

	@Autowired
	BuildingRepository buildingRepository;
	@Autowired
	AddressRepository addressRepository;

	public Building findOne(Long id) {
		return buildingRepository.findOne(id);
	}

	public Page<Building> findAll(Pageable page) {
		return buildingRepository.findAll(page);
	}

	public Building save(Building building) {
		building.setAddress(addressRepository.save(building.getAddress()));
		return buildingRepository.save(building);
	}

	public boolean remove(Long id) {
		try {
			buildingRepository.delete(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Building findByAddress(String street, String number, String city) {
		return buildingRepository.findByAddress(street, number, city);
	}
	
	public Long getCountOfBuilding(){
		return buildingRepository.count();
	}

}
