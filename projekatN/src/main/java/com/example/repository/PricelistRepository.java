package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Pricelist;

public interface PricelistRepository extends JpaRepository<Pricelist, Long>  {

	@Query("SELECT p FROM Pricelist p WHERE p.company.id=?1")
	Pricelist findOneByCompany(Long id);

	@Query("SELECT p FROM Pricelist p WHERE p.type.id=?1")
	List<Pricelist> findOneByGlitchType(Long id);

}
