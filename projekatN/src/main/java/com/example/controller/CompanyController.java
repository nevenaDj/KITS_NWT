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
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserDTO;
import com.example.model.User;
import com.example.security.TokenUtils;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static com.example.utils.Constants.ROLE_COMPANY;

@RestController
@RequestMapping(value = "/api/companies")
@Api(value = "companies")
public class CompanyController {
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;

	@Autowired
	TokenUtils tokenUtils;
	
	

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Add a company.", notes = "Returns the company.", httpMethod = "POST",
				produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponse(code = 201, message = "Created", response = UserDTO.class)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	/*** add a company ***/
	public ResponseEntity<UserDTO> addCompany(
			@ApiParam(value = "The userDTO object", required = true) @RequestBody UserDTO userDTO) {
		User user = UserDTO.getUser(userDTO);
		user.setPassword(passwordEncoder.encode("password"));

		user = userService.save(user, ROLE_COMPANY);
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Get a list of companies.", httpMethod = "GET")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	/***  get a list of companies ***/
	public ResponseEntity<List<UserDTO>> getCompanies(Pageable page) {
		Page<User> users = userService.find(page, ROLE_COMPANY);

		List<UserDTO> usersDTO = new ArrayList<>();
		for (User user : users) {
			usersDTO.add(new UserDTO(user));
		}
		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete a company.", httpMethod = "DELETE")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not found")})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	/*** delete a company ***/
	public ResponseEntity<Void> deleteCompany(
			@ApiParam(value = "The ID of the company.", required = true)@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			userService.remove(id, ROLE_COMPANY);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
