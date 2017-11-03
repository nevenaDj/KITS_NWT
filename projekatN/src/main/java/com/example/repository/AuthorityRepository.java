package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	public Authority findByName(String name);

}
