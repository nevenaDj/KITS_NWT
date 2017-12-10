package com.example.controller;

import static com.example.utils.Constants.TOKEN;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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

import com.example.dto.GlitchDTO;
import com.example.dto.UserDTO;
import com.example.model.Apartment;
import com.example.model.Building;
import com.example.model.Glitch;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.ApartmentService;
import com.example.service.BuildingService;
import com.example.service.GlitchService;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
@Api(value = "glitches")
public class GlitchController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	GlitchService glitchService;

	@Autowired
	UserService userService;

	@Autowired
	ApartmentService apartmentService;

	@Autowired
	BuildingService buildingService;

	@RequestMapping(value = "/apartments/{id}/glitches", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Create a glitch in an apartment.", notes = "Returns the glitch being saved.", httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = GlitchDTO.class),
			@ApiResponse(code = 500, message = "Failure") })
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER')")
	public ResponseEntity<GlitchDTO> addGlitch(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable Long id,
			@ApiParam(value = "The glitchDTO object", required = true) @RequestBody GlitchDTO glitchDTO,
			HttpServletRequest request) {
		String token = request.getHeader(TOKEN);
		String username = tokenUtils.getUsernameFromToken(token);

		Glitch glitch = GlitchDTO.getGlitch(glitchDTO);

		User company = userService.findOne(glitchDTO.getCompanyID());

		if (company != null) {
			glitch.setCompany(company);
		}

		User tenant = userService.findByUsername(username);
		glitch.setTenant(tenant);

		Apartment apartment = apartmentService.findOne(id);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		glitch.setApartment(apartment);

		if (glitch.getResponsiblePerson() == null) {
			Building building = buildingService.findOne(apartment.getBuilding().getId());
			glitch.setResponsiblePerson(building.getPresident());
		}

		glitch = glitchService.saveNewGlitch(glitch, "REPORTED");

		return new ResponseEntity<>(new GlitchDTO(glitch), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/glitches", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_OWNER', 'ROLE_COMPANY')")
	@ApiOperation(value = "Get a list of glitches.", httpMethod = "GET")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	public ResponseEntity<List<GlitchDTO>> getGlitches(Pageable page, HttpServletRequest request) {
		String token = request.getHeader(TOKEN);
		String username = tokenUtils.getUsernameFromToken(token);

		User user = userService.findByUsername(username);

		Page<Glitch> glitches = glitchService.findGlitches(page, user);

		List<GlitchDTO> glitchesDTO = new ArrayList<>();

		for (Glitch glitch : glitches) {
			glitchesDTO.add(new GlitchDTO(glitch));
		}

		return new ResponseEntity<>(glitchesDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/glitches/{id}/responsiblePerson", method = RequestMethod.PUT)
	@ApiOperation(value = "Change the responsible person for the glitch.", notes = "Returns the glitch being saved.", httpMethod = "PUT", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = GlitchDTO.class),
			@ApiResponse(code = 500, message = "Failure"), @ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasRole('ROLE_PRESIDENT')")
	public ResponseEntity<GlitchDTO> changeResponsiblePerson(
			@ApiParam(value = "The ID of the glitch.", required = true) @PathVariable Long id,
			@ApiParam(value = "The userDTO object", required = true) @RequestBody UserDTO userDTO) {
		Glitch glitch = glitchService.findOne(id);

		if (glitch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		User user = userService.findByUsername(userDTO.getUsername());

		if (user != null) {
			glitch.setResponsiblePerson(user);
			glitch = glitchService.save(glitch);
		}

		return new ResponseEntity<>(new GlitchDTO(glitch), HttpStatus.OK);

	}

	@RequestMapping(value = "/apartments/{id}/glitches/{glitch_id}/company/{c_id}", method = RequestMethod.PUT, produces = "application/json")
	@ApiOperation(value = "Change the company for the glitch.", notes = "Returns the glitch being saved.", httpMethod = "PUT", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = GlitchDTO.class),
			@ApiResponse(code = 500, message = "Failure"), @ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasAnyRole('ROLE_PRESIDENT', 'ROLE_COMPANY')")
	public ResponseEntity<GlitchDTO> changeCompany(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable("id") Long apartmentId,
			@ApiParam(value = "The ID of the glitch.", required = true) @PathVariable("glitch_id") Long glitchId, 
			@ApiParam(value = "The ID of the company.", required = true) @PathVariable("c_id") Long companyId) {

		User company = userService.findOne(companyId);

		if (company != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Apartment apartment = apartmentService.findOne(apartmentId);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Glitch glitch = glitchService.findOne(glitchId);

		if (glitch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		glitch.setCompany(company);
		glitchService.save(glitch);

		return new ResponseEntity<>(new GlitchDTO(glitch), HttpStatus.OK);
	}

	@RequestMapping(value = "/apartments/{id}/glitches/{glitch_id}", method = RequestMethod.PUT, produces = "application/json", params = {
			"date" })
	@ApiOperation(value = "Change the date of repair for the glitch.", notes = "Returns the glitch being saved.", httpMethod = "PUT", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = GlitchDTO.class),
			@ApiResponse(code = 500, message = "Failure"), @ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasAnyRole('ROLE_COMPANY')")
	public ResponseEntity<GlitchDTO> changeDateOfRepair(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable("id") Long apartmentId,
			@ApiParam(value = "The ID of the glitch.", required = true) @PathVariable("glitch_id") Long glitchId,
			@ApiParam(name="date", value = "date", required = true) @RequestParam("date") Date dateOfReparing) {

		Apartment apartment = apartmentService.findOne(apartmentId);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Glitch glitch = glitchService.findOne(glitchId);

		if (glitch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		glitch.setDateOfRepair(dateOfReparing);
		glitchService.save(glitch);

		return new ResponseEntity<>(new GlitchDTO(glitch), HttpStatus.OK);
	}

	@RequestMapping(value = "/apartments/{id}/glitches/{glitch_id}", method = RequestMethod.PUT, produces = "application/json")
	@ApiOperation(value = "Approve time of repair for the glitch.", notes = "Returns the glitch being saved.", httpMethod = "PUT", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = GlitchDTO.class),
			@ApiResponse(code = 500, message = "Failure"), @ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<GlitchDTO> approveTimeOfRepaing(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable("id") Long apartmentId,
			@ApiParam(value = "The ID of the glitch.", required = true) @PathVariable("glitch_id") Long glitchId,
			HttpServletRequest request) {

		Apartment apartment = apartmentService.findOne(apartmentId);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Glitch glitch = glitchService.findOne(glitchId);

		if (glitch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);
		User responsible = userService.findByUsername(username);
		if (glitch.getResponsiblePerson().getId() != responsible.getId()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		glitch.setDateOfRepairApproved(true);
		glitchService.save(glitch);

		return new ResponseEntity<>(new GlitchDTO(glitch), HttpStatus.OK);
	}

	@RequestMapping(value = "/apartments/{id}/glitches/{glitch_id}/photo", method = RequestMethod.PUT, params = {
			"image" })
	@ApiOperation(value = "Save a photo for glitch.", notes = "Returns the glitch being saved.", httpMethod = "PUT", 
	produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = GlitchDTO.class),
			@ApiResponse(code = 500, message = "Failure"), @ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<GlitchDTO> addPhoto(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable("id") Long apartmentId,
			@ApiParam(value = "The ID of the glitch.", required = true) @PathVariable("glitch_id") Long glitchId,
			HttpServletRequest request, 
			@ApiParam(name="image", value = "Image", required = true) @RequestParam("image") String image) {

		Apartment apartment = apartmentService.findOne(apartmentId);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Glitch glitch = glitchService.findOne(glitchId);

		if (glitch == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		File file = new File(image);
		byte[] bFile = new byte[(int) file.length()];

		try (FileInputStream fileInputStream = new FileInputStream(file)) {

			int count = fileInputStream.read(bFile);
			
		} catch (IOException e) {
			e.getMessage();
		}

		Set<byte[]> images = new HashSet<>();
		images.add(bFile);

		glitch.setImage(images);
		glitchService.save(glitch);

		return new ResponseEntity<>(new GlitchDTO(glitch), HttpStatus.OK);
	}

}
