package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.GlitchType;

public interface GlitchTypeRepository extends JpaRepository<GlitchType, Long> {

	@Query("Select g FROM GlitchType g WHERE g.type=?1")
	GlitchType findGlitchTypeByName(String name);

}
