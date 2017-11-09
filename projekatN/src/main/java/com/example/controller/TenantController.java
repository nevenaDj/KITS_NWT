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
import com.example.service.UserService;

@RestController
@RequestMapping(value = "/tenants")
public class TenantController {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDTO> addTenant(@RequestBody UserDTO userDTO) {
		User user = UserDTO.getUser(userDTO);
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

		user = userService.save(user, "ROLE_USER");
		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getTenants(Pageable page) {
		Page<User> users = userService.find(page, "ROLE_USER");

		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		for (User user : users) {
			usersDTO.add(new UserDTO(user));
		}
		return new ResponseEntity<List<UserDTO>>(usersDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<UserDTO> updateTenant(@RequestBody UserDTO userDTO) {
		User user = userService.findOne(userDTO.getId());

		if (user == null) {
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		}

		user.setEmail(userDTO.getEmail());
		user.setAddress(userDTO.getAddress());
		user.setPhoneNo(userDTO.getPhoneNo());

		user = userService.update(user);

		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
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

}
