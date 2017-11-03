package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Apartment;
import com.example.repository.ApartmentRepository;

@Service
public class ApartmentService {
	@Autowired
	ApartmentRepository apartmentRepository;

	public Apartment findOne(Long id) {
		return apartmentRepository.findOne(id);
	}

	public Page<Apartment> findAll(Pageable page) {
		return apartmentRepository.findAll(page);
	}

	public Apartment save(Apartment apartment) {
		return apartmentRepository.save(apartment);
	}

	public void remove(Long id) {
		apartmentRepository.delete(id);
	}

}
