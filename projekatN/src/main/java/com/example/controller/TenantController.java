
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
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.GlitchDTO;
import com.example.dto.UserDTO;
import com.example.model.Apartment;
import com.example.model.Glitch;
import com.example.model.User;
import com.example.model.UserAparment;
import com.example.security.TokenUtils;
import com.example.service.ApartmentService;
import com.example.service.GlitchService;
import com.example.service.UserApartmentService;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static com.example.utils.Constants.ROLE_TENANT;

@RestController
@RequestMapping(value = "/api")
@Api(value = "tenants")
public class TenantController {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserService userService;
	@Autowired
	GlitchService glitchService;
	@Autowired
	ApartmentService apartmentService;
	@Autowired
	UserApartmentService userApartmentService;
	@Autowired
	TokenUtils tokenUtils;

	@RequestMapping(value = "/aparments/{id}/tenants", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Add tenant to the apartment.", notes = "Returns the tenant.", httpMethod = "POST", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = UserDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	/*** add tenant ***/
	public ResponseEntity<UserDTO> addTenant(
			@ApiParam(value = "The ID of the apartment.", required = true) @PathVariable Long id,
			@ApiParam(value = "The userDTO object", required = true) @RequestBody UserDTO userDTO) {

		Apartment apartment = apartmentService.findOne(id);

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		User user = userService.findByUsernameAndAuthority(userDTO.getUsername(), ROLE_TENANT);

		if (user == null) {
			user = UserDTO.getUser(userDTO);
			user.setPassword(passwordEncoder.encode("password"));
			user = userService.save(user, ROLE_TENANT);
		}
		UserAparment userApartment = new UserAparment(user, apartment);
		userApartmentService.save(userApartment);

		return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/tenants", method = RequestMethod.GET)
	@ApiOperation(value = "Get a list of tenants.", httpMethod = "GET")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	/*** get list of tenants ***/
	public ResponseEntity<List<UserDTO>> getTenants(Pageable page) {
		Page<User> users = userService.find(page, ROLE_TENANT);

		List<UserDTO> usersDTO = new ArrayList<>();
		for (User user : users) {
			usersDTO.add(new UserDTO(user));
		}
		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/tenants/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a tenant.", httpMethod = "DELETE")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not found") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	/*** delete tenant ***/
	public ResponseEntity<Void> deleteTenant(
			@ApiParam(value = "The ID of the tenant.", required = true) @PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			userService.remove(id, ROLE_TENANT);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/responsibleGlitches", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Find glitches where the tenant is responsible person.", httpMethod = "GET")
	@ApiImplicitParam(paramType = "header", name = "X-Auth-Token", required = true, value = "JWT token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not found") })
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<GlitchDTO>> getGlitches(HttpServletRequest request, Pageable page) {
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		User tenant = userService.findByUsername(username);

		Page<Glitch> glitches = glitchService.findByResponsibility(page, tenant.getId());

		List<GlitchDTO> glitchesDTO = new ArrayList<>();
		for (Glitch glitch : glitches) {
			glitchesDTO.add(new GlitchDTO(glitch));
		}

		return new ResponseEntity<>(glitchesDTO, HttpStatus.OK);

	}

}
