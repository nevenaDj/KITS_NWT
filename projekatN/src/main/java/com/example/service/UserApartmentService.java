package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Apartment;
import com.example.model.User;
import com.example.model.UserAparment;
import com.example.repository.UserApatmentRepository;

@Service
public class UserApartmentService {

	@Autowired
	UserApatmentRepository userApartmentRepostitory;

	public UserAparment save(UserAparment userApartment) {
		return userApartmentRepostitory.save(userApartment);

	}

	public List<User> getTenants(Long idApartment) {
		return userApartmentRepostitory.getTenants(idApartment);
	}

	public List<Apartment> getApartments(Long idTenant) {
		return userApartmentRepostitory.getApartmentsOfTenant(idTenant);
	}
}
