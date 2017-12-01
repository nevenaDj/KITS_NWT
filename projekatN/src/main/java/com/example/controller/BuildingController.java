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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AddressDTO;
import com.example.dto.BuildingDTO;
import com.example.dto.UserDTO;
import com.example.model.Building;
import com.example.model.User;
import com.example.service.BuildingService;
import com.example.service.UserService;

@RestController
@RequestMapping(value = "/buildings")
public class BuildingController {

	@Autowired
	BuildingService buildingService;

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<BuildingDTO>> getBuildings(Pageable page) {
		Page<Building> buildings = buildingService.findAll(page);

		List<BuildingDTO> buildingsDTO = new ArrayList<BuildingDTO>();
		for (Building building : buildings) {
			buildingsDTO.add(new BuildingDTO(building));
		}
		return new ResponseEntity<List<BuildingDTO>>(buildingsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<BuildingDTO> getBuilding(@PathVariable Long id) {
		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<BuildingDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<BuildingDTO>(new BuildingDTO(building), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<BuildingDTO> addBuilding(@RequestBody BuildingDTO buildingDTO) {
		Building building = BuildingDTO.getBuilding(buildingDTO);

		building = buildingService.save(building);

		return new ResponseEntity<BuildingDTO>(new BuildingDTO(building), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<BuildingDTO> updateBuilding(@RequestBody BuildingDTO buildingDTO) {
		Building building = buildingService.findOne(buildingDTO.getId());

		if (building == null) {
			return new ResponseEntity<BuildingDTO>(HttpStatus.BAD_REQUEST);
		}

		building.setAddress(AddressDTO.getAddress(buildingDTO.getAddress()));
		building = buildingService.save(building);

		return new ResponseEntity<BuildingDTO>(new BuildingDTO(building), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			buildingService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.GET, params = { "street", "number", "city" })
	public ResponseEntity<BuildingDTO> findByAddress(@RequestParam("street") String street,
			@RequestParam("number") String number, @RequestParam("city") String city) {
		System.out.println(street);
		System.out.println(number);
		System.out.println(city);

		Building building = buildingService.findByAddress(street, number, city);

		if (building != null) {
			return new ResponseEntity<BuildingDTO>(new BuildingDTO(building), HttpStatus.OK);
		} else {
			return new ResponseEntity<BuildingDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}/president", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDTO> addPresident(@PathVariable Long id, @RequestBody UserDTO userDTO) {
		User user = UserDTO.getUser(userDTO);

		user = userService.save(user, "ROLE_PRESIDENT");

		Building building = buildingService.findOne(id);

		if (building == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		}

		buildingService.setPresident(building.getId(), user);

		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.CREATED);
	}

}
