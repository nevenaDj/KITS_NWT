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

import com.example.dto.AddressDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserPasswordDTO;
import com.example.model.Apartment;
import com.example.model.User;
import com.example.model.UserAparment;
import com.example.service.ApartmentService;
import com.example.service.UserApartmentService;
import com.example.service.UserService;

@RestController
public class TenantController {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserService userService;
	@Autowired
	ApartmentService apartmentService;
	@Autowired
	UserApartmentService userApartmentService;

	@RequestMapping(value = "/aparments/{id}/tenants", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDTO> addTenant(@PathVariable Long id, @RequestBody UserDTO userDTO) {

		Apartment apartment = apartmentService.findOne(id);

		if (apartment == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		}

		User user = userService.findByUsernameAndAuthority(userDTO.getUsername(), "ROLE_USER");

		if (user == null) {
			user = UserDTO.getUser(userDTO);
			user.setPassword(passwordEncoder.encode("password"));
			user = userService.save(user, "ROLE_USER");
		}
		UserAparment userApartment = new UserAparment(user, apartment);
		userApartmentService.save(userApartment);

		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/tenants", method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getTenants(Pageable page) {
		Page<User> users = userService.find(page, "ROLE_USER");

		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		for (User user : users) {
			usersDTO.add(new UserDTO(user));
		}
		return new ResponseEntity<List<UserDTO>>(usersDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/tenants", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<UserDTO> updateTenant(@RequestBody UserDTO userDTO) {
		User user = userService.findOne(userDTO.getId());

		if (user == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		}

		user.setEmail(userDTO.getEmail());
		user.setAddress(AddressDTO.getAddress(userDTO.getAddress()));
		user.setPhoneNo(userDTO.getPhoneNo());

		user = userService.update(user);

		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
	}

	@RequestMapping(value = "/tenants/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			userService.remove(id, "ROLE_USER");
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/tenants/{id}/password", method = RequestMethod.PUT, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_USER')")
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
