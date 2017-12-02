package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.AgendaPoint;

public interface AgendaPointRepository extends JpaRepository<AgendaPoint, Long> {

}
