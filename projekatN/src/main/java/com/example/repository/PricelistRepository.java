package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Pricelist;

public interface PricelistRepository extends JpaRepository<Pricelist, Long>  {

	@Query("SELECT p FROM Pricelist p WHERE p.company.id=?1")
	Pricelist findOneByCompany(Long id);

}
