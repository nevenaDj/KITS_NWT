package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static com.example.utils.Constants.ROLE_PRESIDENT;

@RestController
@RequestMapping(value = "/api/buildings")
@Api(value = "buildings")
public class BuildingController {

	@Autowired
	BuildingService buildingService;

	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Get a list of buildings.", httpMethod = "GET")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "string", paramType = "query",
                value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "string", paramType = "query",
                value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                value = "Sorting criteria in the format: property(,asc|desc). " +
                        "Default sort order is ascending. " +
                        "Multiple sort criteria are supported."),
		@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")})
	public ResponseEntity<List<BuildingDTO>> getBuildings(Pageable page) {
		Page<Building> buildings = buildingService.findAll(page);

		List<BuildingDTO> buildingsDTO = new ArrayList<>();
		for (Building building : buildings) {
			buildingsDTO.add(new BuildingDTO(building));
		}
		return new ResponseEntity<>(buildingsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get a single building.", httpMethod = "GET")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = BuildingDTO.class),
			@ApiResponse(code = 404, message = "Not found") })
	public ResponseEntity<BuildingDTO> getBuilding(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id) {
		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new BuildingDTO(building), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Create a building.", notes = "Returns the building being saved.", httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = BuildingDTO.class),
			@ApiResponse(code = 500, message = "Failure") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<BuildingDTO> addBuilding(
			@ApiParam(value = "The bulidingDTO object", required = true) @RequestBody BuildingDTO buildingDTO) {
		Building building = BuildingDTO.getBuilding(buildingDTO);

		building = buildingService.save(building);

		return new ResponseEntity<>(new BuildingDTO(building), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiOperation(value = "Update a building.", notes = "Returns the building being saved.", httpMethod = "PUT", produces = "application/json", consumes = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = BuildingDTO.class),
			@ApiResponse(code = 500, message = "Failure"), @ApiResponse(code = 400, message = "Bad request") })
	public ResponseEntity<BuildingDTO> updateBuilding(
			@ApiParam(value = "The bulidingDTO object", required = true) @RequestBody BuildingDTO buildingDTO) {
		Building building = buildingService.findOne(buildingDTO.getId());

		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		building.setAddress(AddressDTO.getAddress(buildingDTO.getAddress()));
		building = buildingService.save(building);

		return new ResponseEntity<>(new BuildingDTO(building), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a buildig.", httpMethod = "DELETE")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not found")})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteBuilding(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id) {
		Building building = buildingService.findOne(id);
		if (building == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			buildingService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.GET, params = { "street", "number", "city" })
	@ApiOperation(value = "Find a building by address.", httpMethod = "GET")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = BuildingDTO.class),
			@ApiResponse(code = 404, message = "Not found") })
	public ResponseEntity<BuildingDTO> findBuildingByAddress(
			@ApiParam(name = "street", value = "street",required=true) @RequestParam("street") String street,
			@ApiParam(name = "number", value = "number of street",required=true) @RequestParam("number") String number,
			@ApiParam(name = "city", value = "city",required=true) @RequestParam("city") String city) {

		Building building = buildingService.findByAddress(street, number, city);

		if (building != null) {
			return new ResponseEntity<>(new BuildingDTO(building), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}/president", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Add a building president.", httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = BuildingDTO.class),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 500, message = "Failure") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<BuildingDTO> addPresident(@PathVariable Long id, @RequestBody UserDTO userDTO) {
		
		User user = null;
		if (userDTO.getId() == null){
			user = UserDTO.getUser(userDTO);
			user.setPassword(passwordEncoder.encode("password"));
			user = userService.save(user, ROLE_PRESIDENT);
		}else{
			user = userService.findOne(userDTO.getId());
			
			if (user == null){
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
			userService.saveUserAuthority(user, ROLE_PRESIDENT);
		}
		
		Building building = buildingService.findOne(id);

		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		building.setPresident(user);
		building = buildingService.save(building);

		return new ResponseEntity<>(new BuildingDTO(building), HttpStatus.CREATED);
	}

}
