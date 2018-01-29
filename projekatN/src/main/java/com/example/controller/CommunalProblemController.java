package com.example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

import com.example.dto.CommunalProblemDTO;
import com.example.model.Apartment;
import com.example.model.Building;
import com.example.model.CommunalProblem;
import com.example.model.User;
import com.example.service.ApartmentService;
import com.example.service.BuildingService;
import com.example.service.CommunalProblemService;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
@Api(value = "communal problems")
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
	@ApiOperation(value = "Create a communal problem in a building.", notes = "Returns the communal problem being saved.", 
				httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = CommunalProblemDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	/*** add a communal problem ***/
	public ResponseEntity<CommunalProblemDTO> addCommunalProblem(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id,
			@ApiParam(value = "The communalProblemDTO object", required = true) @RequestBody CommunalProblemDTO communalProblemDTO) {
		CommunalProblem communalProblem = CommunalProblemDTO.getCommunalProblem(communalProblemDTO);

		Building building = buildingService.findOne(id);

		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		communalProblem.setBuilding(building);	
		if (communalProblemDTO.getCompanyID() != null){
			User company = userService.findOne(communalProblemDTO.getCompanyID());
			if (company != null) {
				communalProblem.setCompany(company);
			}
		}

		communalProblemService.save(communalProblem);

		return new ResponseEntity<>(new CommunalProblemDTO(communalProblem), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/communalProblems/{id}", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get a communal problems in a building.", 
				httpMethod = "GET", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = CommunalProblemDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** get a communal problem ***/
	public ResponseEntity<CommunalProblemDTO> getCommunalProblems(
			@ApiParam(value = "The ID of the communal proble.", required = true) @PathVariable Long id) {
		CommunalProblem problem = communalProblemService.findOne(id);
		if (problem == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new CommunalProblemDTO(problem), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buildings/{id}/communalProblems", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get a communal problems in a building.", 
				httpMethod = "GET", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = CommunalProblemDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** get a communal problem ***/
	public ResponseEntity<List<CommunalProblemDTO>> getCommunalProblems(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id,
			Pageable page) {
		Building building = buildingService.findOne(id);

		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Page<CommunalProblem> problems= communalProblemService.findProblemsByBuilding(id, page);
		List<CommunalProblemDTO> problemsDTO = new ArrayList<CommunalProblemDTO>();
		
		for (CommunalProblem communalProblem : problems) {
			problemsDTO.add(new CommunalProblemDTO(communalProblem));
		}
		return new ResponseEntity<>(problemsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buildings/{id}/communalProblems/active", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get a communal problems in a building.", 
				httpMethod = "GET", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = CommunalProblemDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** get a communal problem ***/
	public ResponseEntity<List<CommunalProblemDTO>> getActiveCommunalProblems(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id,
			Pageable page) {
		Building building = buildingService.findOne(id);

		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	//	Page<CommunalProblem> problems= communalProblemService.findActiveProblemsByBuilding(id, page);
		
		List<CommunalProblem> problems= communalProblemService.findProblemsByBuilding(id);
		List<CommunalProblemDTO> problemsDTO = new ArrayList<CommunalProblemDTO>();
		
		Date today= new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		
		for (CommunalProblem communalProblem : problems) {
			if (communalProblem.getDateOfRepair()!=null)
				if (today.before(communalProblem.getDateOfRepair()))
					problemsDTO.add(new CommunalProblemDTO(communalProblem));
		}
	
		return new ResponseEntity<>(problemsDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buildings/{id}/communalProblems/count", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get a communal problems in a building.", 
				httpMethod = "GET", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = CommunalProblemDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** get a communal problem ***/
	public ResponseEntity<Integer> getCommunalProblemsCount(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id) {
		Building building = buildingService.findOne(id);

		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Integer count= communalProblemService.findProblemsByBuildingCount(id);
	
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buildings/{id}/communalProblems/active/count", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Get a communal problems in a building.", 
				httpMethod = "GET", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created", response = CommunalProblemDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** get a communal problem ***/
	public ResponseEntity<Integer> getActiveCommunalProblemsCount(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id) {
		Building building = buildingService.findOne(id);

		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		List<CommunalProblem> problems= communalProblemService.findProblemsByBuilding(id);
		List<CommunalProblemDTO> problemsDTO = new ArrayList<CommunalProblemDTO>();
		
		Date today= new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		
		for (CommunalProblem communalProblem : problems) {
			if (communalProblem.getDateOfRepair()!=null)
				if (today.before(communalProblem.getDateOfRepair()))
					problemsDTO.add(new CommunalProblemDTO(communalProblem));
		}
		//Integer count= communalProblemService.findActiveProblemsByBuildingCount(id);
		Integer count= problemsDTO.size();
		
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/communalProblems/{id}/apartments/{id_apartment}", method = RequestMethod.PUT)
	@ApiOperation(value = "Add communal problem in apartment.", httpMethod = "PUT", produces = "application/json", consumes = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = CommunalProblemDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_OWNER')")
	/*** add communal problem apartment ***/
	public ResponseEntity<CommunalProblemDTO> addCommunalProblemApartment(
			@ApiParam(value = "The ID of the communal problem.", required = true) @PathVariable("id") Long id,
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable("id_apartment") Long idApartment) {
		CommunalProblem communalProblem = communalProblemService.findOne(id);

		if (communalProblem == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Apartment apartment = apartmentService.findOne(idApartment);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		communalProblem.addApartment(apartment);
		communalProblem = communalProblemService.save(communalProblem);
		
		return new ResponseEntity<>(new CommunalProblemDTO(communalProblem), HttpStatus.OK);

	}
	
	@RequestMapping(value = "/communalProblems/{id}/apartments/{id_apartment}", method = RequestMethod.DELETE)
	@ApiOperation(value = "From communal problem delete apartment.", httpMethod = "DELETE", produces = "application/json", consumes = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = CommunalProblemDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_OWNER')")
	/*** add communal problem apartment ***/
	public ResponseEntity<CommunalProblemDTO> deleteCommunalProblemApartment(
			@ApiParam(value = "The ID of the communal problem.", required = true) @PathVariable("id") Long id,
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable("id_apartment") Long idApartment) {
		CommunalProblem communalProblem = communalProblemService.findOne(id);

		if (communalProblem == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Apartment apartment = apartmentService.findOne(idApartment);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Set<Apartment> apartments = communalProblem.getApartments();
		apartments.remove(apartment);
		communalProblem.setApartments(apartments);
		communalProblem = communalProblemService.save(communalProblem);

		
		return new ResponseEntity<>(new CommunalProblemDTO(communalProblem), HttpStatus.OK);

	}

}
