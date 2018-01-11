package com.example.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.User;
import com.example.model.UserAparment;


public interface UserApatmentRepository extends JpaRepository<UserAparment, Long> {
	@Query("SELECT u FROM User u, UserAparment a WHERE a.apartment.id = ?1 AND a.tenant.id = u.id")
	public List<User> getTenants(Long idApartment);
	
	@Query("SELECT a.id FROM UserAparment a WHERE a.tenant.id=?1 AND a.apartment.id=?2")
	public Long get(Long idTenant, Long idApartment);

}
