package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.GlitchState;

public interface GlitchStateRepository extends JpaRepository<GlitchState, Long> {

}
