package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
	
	@Query("SELECT u FROM User u, UserAuthority au, Authority a WHERE a.name = ?1 AND au.authority.id = a.id AND au.user.id = u.id")
	public Page<User> find(String role, Pageable page);

}
