package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Building;

public interface BuildingRepository extends JpaRepository<Building, Long> {

}
