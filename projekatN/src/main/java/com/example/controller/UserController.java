package com.example.controller;

import java.util.ArrayList;
import java.util.List;

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

@RestController
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
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
					loginDTO.getPassword());
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());
			return new ResponseEntity<String>(tokenUtils.generateToken(details), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>("Invalid login", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> register(@RequestBody RegisterDTO registerDTO) {
		if (!registerDTO.getPassword().equals(registerDTO.getPassword2())) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

		Apartment apartment = aparmentService.findOne(registerDTO.getApartmentId());

		if (apartment == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

		User user = RegisterDTO.getTenant(registerDTO);
		user = userService.save(user, "ROLE_USER");

		UserAparment userApartment = new UserAparment(user, apartment);
		userApartmentService.save(userApartment);

		return new ResponseEntity<Void>(HttpStatus.CREATED);

	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<UserDTO>> getUsers(Pageable page) {
		Page<User> users = userService.findAll(page);

		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		for (User user : users) {
			usersDTO.add(new UserDTO(user));
		}

		return new ResponseEntity<List<UserDTO>>(usersDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
	}

	@RequestMapping(value = "admin/{id}/password", method = RequestMethod.PUT, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody UserPasswordDTO userPasswordDTO) {
		boolean flag = userService.changePassword(id, userPasswordDTO.getCurrentPassword(),
				userPasswordDTO.getNewPassword1(), userPasswordDTO.getNewPassword2());

		if (flag == true) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

	}
}
