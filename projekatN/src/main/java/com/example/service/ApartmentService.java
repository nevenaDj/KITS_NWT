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

	public Page<Apartment> findApartments(Long idBuilding, Pageable page) {
		return apartmentRepository.findApartments(idBuilding, page);
	}

	public Apartment save(Apartment apartment) {
		return apartmentRepository.save(apartment);
	}

	public void remove(Long id) {
		apartmentRepository.delete(id);
	}

	public Apartment findByAddress(String street, String number, String city, int numberApartment) {
		return apartmentRepository.findByAddress(street, number, city, numberApartment);
	}

}
