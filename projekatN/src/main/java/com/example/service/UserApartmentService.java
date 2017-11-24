package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.UserAparment;
import com.example.repository.UserApatmentRepository;

@Service
public class UserApartmentService {

	@Autowired
	UserApatmentRepository userApartmentRepostitory;

	public UserAparment save(UserAparment userApartment) {
		return userApartmentRepostitory.save(userApartment);

	}
}
