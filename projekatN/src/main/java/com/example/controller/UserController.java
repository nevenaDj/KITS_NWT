package com.example.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AddressDTO;
import com.example.dto.LoginDTO;
import com.example.dto.RegisterDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserPasswordDTO;
import com.example.model.Apartment;
import com.example.model.User;
import com.example.model.UserAparment;
import com.example.security.TokenUtils;
import com.example.service.ApartmentService;
import com.example.service.UserApartmentService;
import com.example.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api")
@Api(value = "users")
public class UserController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ApartmentService aparmentService;

	@Autowired
	UserApartmentService userApartmentService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Log in.", notes = "Returns the JWT token.", httpMethod = "POST", consumes = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** login ***/
	public ResponseEntity<String> login(
			@ApiParam(value = "The loginDTO object", required = true) @RequestBody LoginDTO loginDTO) {
		
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
					loginDTO.getPassword());
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());
			return new ResponseEntity<>(tokenUtils.generateToken(details), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>("Invalid login", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Registration.", httpMethod = "POST", consumes = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** registration ***/
	public ResponseEntity<Void> register(
			@ApiParam(value = "The registerDTO object", required = true) @RequestBody RegisterDTO registerDTO) {
		if (!registerDTO.getPassword().equals(registerDTO.getPassword2())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Apartment apartment = aparmentService.findOne(registerDTO.getApartmentId());

		if (apartment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		User user = RegisterDTO.getTenant(registerDTO);
		user = userService.save(user, "ROLE_USER");

		UserAparment userApartment = new UserAparment(user, apartment);
		userApartmentService.save(userApartment);

		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ApiOperation(value = "Get a list of users.", httpMethod = "GET")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	/*** get list of users ***/
	public ResponseEntity<List<UserDTO>> getUsers(Pageable page) {
		Page<User> users = userService.findAll(page);

		List<UserDTO> usersDTO = new ArrayList<>();
		for (User user : users) {
			usersDTO.add(new UserDTO(user));
		}

		return new ResponseEntity<>(usersDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get a user.", httpMethod = "GET")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response=UserDTO.class),
			@ApiResponse(code = 404, message = "Not found") })
	/*** get user by id ***/
	public ResponseEntity<UserDTO> getUser(
			@ApiParam(value = "The ID of the user.", required = true) @PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
	}
	
	@RequestMapping(value="/users", method = RequestMethod.PUT, consumes = "application/json")
	@ApiOperation(value = "Update a user.", notes = "Returns the company being saved.", httpMethod = "PUT", 
				produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = UserDTO.class),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** update user ***/
	public ResponseEntity<UserDTO> updateUser(
			@ApiParam(value = "The userDTO object", required = true)@RequestBody UserDTO userDTO) {
		User user = userService.findOne(userDTO.getId());

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		user.setEmail(userDTO.getEmail());
		user.setAddress(AddressDTO.getAddress(userDTO.getAddress()));
		user.setPhoneNo(userDTO.getPhoneNo());

		user = userService.update(user);

		return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
	}


	@RequestMapping(value = "/users/password", method = RequestMethod.PUT, consumes = "application/json")
	@ApiOperation(value = "Change password.", httpMethod = "PUT", produces = "application/json", consumes = "application/json")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request") })
	/*** change user password ***/
	public ResponseEntity<Void> changePassword(
			@ApiParam(value = "The userPasswordDTO object", required = true) @RequestBody UserPasswordDTO userPasswordDTO,
			HttpServletRequest request) {
		
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);

		boolean flag = userService.changePassword(username, userPasswordDTO.getCurrentPassword(),
				userPasswordDTO.getNewPassword1(), userPasswordDTO.getNewPassword2());

		if (flag) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@RequestMapping(value = "/me", method = RequestMethod.GET)
	@ApiOperation(value = "Get the loged in user.", httpMethod = "GET")
	@ApiImplicitParam(paramType="header", name="X-Auth-Token", required=true, value="JWT token")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response=UserDTO.class),
			@ApiResponse(code = 404, message = "Not found") })
	/*** get user ***/
	public ResponseEntity<UserDTO> getLogedINUser(HttpServletRequest request) {
		System.out.println("Kezdes");
		String token = request.getHeader("X-Auth-Token");
		System.out.println(token);
		String username = tokenUtils.getUsernameFromToken(token);
		System.out.println(username);
		User user = userService.findByUsername(username);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		System.out.println(user.getUsername());
		System.out.println(user.getEmail());
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
	}
}
