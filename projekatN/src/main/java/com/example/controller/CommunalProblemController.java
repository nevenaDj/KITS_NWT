package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CommunalProblemDTO;
import com.example.model.Apartment;
import com.example.model.Building;
import com.example.model.CommunalProblem;
import com.example.model.User;
import com.example.service.ApartmentService;
import com.example.service.BuildingService;
import com.example.service.CommunalProblemService;
import com.example.service.UserService;

@RestController
public class CommunalProblemController {
	@Autowired
	CommunalProblemService communalProblemService;
	@Autowired
	BuildingService buildingService;
	@Autowired
	UserService userService;
	@Autowired
	ApartmentService apartmentService;

	@RequestMapping(value = "/buildings/{id}/communalProblems", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<CommunalProblemDTO> addCommunalProblem(@PathVariable Long id,
			@RequestBody CommunalProblemDTO communalProblemDTO) {
		CommunalProblem communalProblem = CommunalProblemDTO.getCommunalProblem(communalProblemDTO);

		Building building = buildingService.findOne(id);

		if (building == null) {
			return new ResponseEntity<CommunalProblemDTO>(HttpStatus.BAD_REQUEST);
		}

		communalProblem.setBuilding(building);

		User company = userService.findOne(communalProblemDTO.getCompanyID());

		if (company != null) {
			communalProblem.setCompany(company);
		}

		communalProblemService.save(communalProblem);

		return new ResponseEntity<CommunalProblemDTO>(new CommunalProblemDTO(communalProblem), HttpStatus.CREATED);

	}

	@RequestMapping(value = "/communalProblems/{id}/apartments/{id_apartment}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<CommunalProblemDTO> addCommunalProblemApartment(@PathVariable("id") Long id,
			@PathVariable("id_apartment") Long idApartment) {
		CommunalProblem communalProblem = communalProblemService.findOne(id);

		if (communalProblem == null) {
			return new ResponseEntity<CommunalProblemDTO>(HttpStatus.BAD_REQUEST);
		}

		Apartment apartment = apartmentService.findOne(idApartment);

		if (apartment == null) {
			return new ResponseEntity<CommunalProblemDTO>(HttpStatus.BAD_REQUEST);
		}

		communalProblem.addApartment(apartment);
		communalProblem = communalProblemService.save(communalProblem);

		return new ResponseEntity<CommunalProblemDTO>(new CommunalProblemDTO(communalProblem), HttpStatus.OK);

	}

}
