package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.example.dto.ApartmentDTO;
import com.example.dto.UserDTO;
import com.example.model.Apartment;
import com.example.model.Building;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.ApartmentService;
import com.example.service.BuildingService;
import com.example.service.UserApartmentService;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static com.example.utils.Constants.ROLE_OWNER;

@RestController
@RequestMapping(value = "/api")
@Api(value = "apartments")
public class ApartmentController {

	@Autowired
	ApartmentService apartmentService;
	@Autowired
	BuildingService buildingService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserService userService;
	@Autowired
	TokenUtils tokenUtils;
	@Autowired
	UserApartmentService userApartmentService;

	@RequestMapping(value = "/buildings/{id}/apartments", method = RequestMethod.GET)
	@ApiOperation(value = "Get a list of apatments in a building.", httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "string", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "string", paramType = "query", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported."),
			@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token") })
	/*** Get list of apartments in a building ***/
	public ResponseEntity<List<ApartmentDTO>> getApartments(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id, Pageable page) {
		Page<Apartment> apartments = apartmentService.findApartments(id, page);

		List<ApartmentDTO> apartmentsDTO = new ArrayList<>();

		for (Apartment apartment : apartments) {
			apartmentsDTO.add(new ApartmentDTO(apartment));
		}

		return new ResponseEntity<>(apartmentsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/apartments/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get a single apartment.", httpMethod = "GET")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ApartmentDTO.class),
			@ApiResponse(code = 404, message = "Not found") })
	/*** get apartment by id ***/
	public ResponseEntity<ApartmentDTO> getApartment(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable Long id) {
		Apartment apartment = apartmentService.findOne(id);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ApartmentDTO(apartment), HttpStatus.OK);
	}

	@RequestMapping(value = "/buildings/{id}/apartments", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Create an apartment in a building.", notes = "Returns the apartment being saved.", httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = ApartmentDTO.class),
			@ApiResponse(code = 500, message = "Failure") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	/*** add new apartment in a building ***/
	public ResponseEntity<ApartmentDTO> addApartment(
			@ApiParam(value = "The ID of the building.", required = true) @PathVariable Long id,
			@ApiParam(value = "The apartmentDTO object", required = true) @RequestBody ApartmentDTO apartmentDTO) {
		Apartment apartment = ApartmentDTO.getApartment(apartmentDTO);
		Building building = buildingService.findOne(id);

		if (building == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		apartment.setBuilding(building);

		apartment = apartmentService.save(apartment);

		return new ResponseEntity<>(new ApartmentDTO(apartment), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/apartments", method = RequestMethod.PUT, consumes = "application/json")
	@ApiOperation(value = "Update an apartment.", notes = "Returns the apartment being saved.", httpMethod = "PUT", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ApartmentDTO.class),
			@ApiResponse(code = 500, message = "Failure"), @ApiResponse(code = 400, message = "Bad request") })
	/*** update an a apartment ***/
	public ResponseEntity<ApartmentDTO> updateApartment(
			@ApiParam(value = "The apartmentDTO object", required = true) @RequestBody ApartmentDTO apartmentDTO) {
		Apartment apartment = apartmentService.findOne(apartmentDTO.getId());

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		apartment.setDescription(apartmentDTO.getDescription());
		apartment.setNumber(apartmentDTO.getNumber());

		apartment = apartmentService.save(apartment);

		return new ResponseEntity<>(new ApartmentDTO(apartment), HttpStatus.OK);
	}

	@RequestMapping(value = "/apartments/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete an apartment.", httpMethod = "DELETE")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not found") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	/*** delete an apartment ***/
	public ResponseEntity<Void> deleteApartment(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable Long id) {
		Apartment apartment = apartmentService.findOne(id);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		boolean retVal = apartmentService.remove(id);
		if (retVal) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/apartment", method = RequestMethod.GET, params = { "street", "number", "city",
			"number_apartment" })
	@ApiOperation(value = "Find an apartment by address.", httpMethod = "GET")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ApartmentDTO.class),
			@ApiResponse(code = 404, message = "Not found") })
	/**** find an apartment by address ***/
	public ResponseEntity<ApartmentDTO> findByAddress(
			@ApiParam(name = "street", value = "street", required = true) @RequestParam("street") String street,
			@ApiParam(name = "number", value = "number", required = true) @RequestParam("number") String number,
			@ApiParam(name = "city", value = "city", required = true) @RequestParam("city") String city,
			@ApiParam(name = "numberApartment", value = "number apartment", required = true) @RequestParam("number_apartment") int numberApartment) {
		Apartment apartment = apartmentService.findByAddress(street, number, city, numberApartment);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ApartmentDTO(apartment), HttpStatus.OK);

	}

	@RequestMapping(value = "/apartments/{id}/owner", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Add an apartment owner.", httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = ApartmentDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	/*** add apartment owner ***/
	public ResponseEntity<ApartmentDTO> addOwner(@PathVariable Long id, @RequestBody UserDTO userDTO) {
		User owner = null;
		if (userDTO.getId() == null) {
			owner = UserDTO.getUser(userDTO);
			owner.setPassword(passwordEncoder.encode("password"));
			owner = userService.save(owner, ROLE_OWNER);
		} else {
			owner = userService.findOne(userDTO.getId());

			if (owner == null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			if (!owner.hasAuthority(ROLE_OWNER)) {
				userService.saveUserAuthority(owner, ROLE_OWNER);
			}
		}
		Apartment apartment = apartmentService.findOne(id);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		apartment.setOwner(owner);
		apartment = apartmentService.save(apartment);

		return new ResponseEntity<>(new ApartmentDTO(apartment), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/apartments/{id}/owner", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete an apartment owner.", httpMethod = "DELETE")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not found") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	/*** delete owner ***/
	public ResponseEntity<ApartmentDTO> deleteTenant(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable Long id) {

		Apartment apartment = apartmentService.findOne(id);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		apartment.setOwner(null);
		apartment = apartmentService.save(apartment);

		return new ResponseEntity<>(new ApartmentDTO(apartment), HttpStatus.OK);

	}

	@RequestMapping(value = "/apartments/my", method = RequestMethod.GET)
	@ApiOperation(value = "Get a current apartment of user.", httpMethod = "GET")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ApartmentDTO.class),
			@ApiResponse(code = 404, message = "Not found") })
	/*** get a current user ***/
	public ResponseEntity<List<ApartmentDTO>> getCurrentUser(HttpServletRequest request) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User user = userService.findByUsername(username);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Apartment> apartments = userApartmentService.getApartments(user.getId());

		List<ApartmentDTO> apartmentsDTO = new ArrayList<>();

		for (Apartment apartment : apartments) {
			apartmentsDTO.add(new ApartmentDTO(apartment));
		}

		return new ResponseEntity<>(apartmentsDTO, HttpStatus.OK);
	}
}
