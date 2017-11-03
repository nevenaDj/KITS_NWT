package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

	@Query("SELECT au.id FROM UserAuthority au WHERE au.authority.id = ?2 AND au.user.id = ?1")
	public Long findByUserAndAuthority(Long userID, Long authorityID);
}
