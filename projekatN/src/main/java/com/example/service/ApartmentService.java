package com.example.service;

import java.util.List;

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

	public boolean remove(Long id) {
		try {
			apartmentRepository.delete(id);
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	public Apartment findByAddress(String street, String number, String city, int numberApartment) {
		return apartmentRepository.findByAddress(street, number, city, numberApartment);
	}

	public List<Apartment> getApartmentsOfOwner(Long id) {
		return apartmentRepository.getApartmentsOfOwner(id);
	}

}
