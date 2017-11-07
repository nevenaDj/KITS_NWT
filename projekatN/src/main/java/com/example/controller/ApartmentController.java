package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ApartmentDTO;
import com.example.model.Apartment;
import com.example.service.ApartmentService;

@RestController
@RequestMapping(value = "api/apartments")
public class ApartmentController {

	@Autowired
	ApartmentService apartmentService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ApartmentDTO>> getApartments(Pageable page) {
		Page<Apartment> apartments = apartmentService.findAll(page);

		List<ApartmentDTO> apartmentsDTO = new ArrayList<ApartmentDTO>();

		for (Apartment apartment : apartments) {
			apartmentsDTO.add(new ApartmentDTO(apartment));
		}

		return new ResponseEntity<List<ApartmentDTO>>(apartmentsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ApartmentDTO> getApartment(@PathVariable Long id) {
		Apartment apartment = apartmentService.findOne(id);

		if (apartment == null) {
			return new ResponseEntity<ApartmentDTO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ApartmentDTO>(new ApartmentDTO(apartment), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ApartmentDTO> addApartment(@RequestBody ApartmentDTO apartmentDTO) {
		Apartment apartment = ApartmentDTO.getApartment(apartmentDTO);

		apartment = apartmentService.save(apartment);

		return new ResponseEntity<ApartmentDTO>(new ApartmentDTO(apartment), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<ApartmentDTO> updateApartment(@RequestBody ApartmentDTO apartmentDTO) {
		Apartment apartment = apartmentService.findOne(apartmentDTO.getId());

		if (apartment == null) {
			return new ResponseEntity<ApartmentDTO>(HttpStatus.BAD_REQUEST);
		}

		apartment.setDescription(apartmentDTO.getDescription());
		apartment.setNumber(apartmentDTO.getNumber());

		apartment = apartmentService.save(apartment);

		return new ResponseEntity<ApartmentDTO>(new ApartmentDTO(apartment), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
		Apartment apartment = apartmentService.findOne(id);

		if (apartment == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			apartmentService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}

	}
}