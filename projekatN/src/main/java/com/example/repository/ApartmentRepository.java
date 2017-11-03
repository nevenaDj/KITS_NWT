package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

}
