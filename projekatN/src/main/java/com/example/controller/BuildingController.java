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

import com.example.dto.BuildingDTO;
import com.example.model.Building;
import com.example.service.BuildingService;

@RestController
@RequestMapping(value = "api/buildings")
public class BuildingController {

	@Autowired
	BuildingService buildingService;

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

		building.setAddress(buildingDTO.getAddress());
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

	@RequestMapping(method = RequestMethod.GET, params = "address")
	public ResponseEntity<List<BuildingDTO>> findByAddress(@RequestParam("address") String address) {
		System.out.println(address);
		return new ResponseEntity<List<BuildingDTO>>(HttpStatus.OK);

	}

}