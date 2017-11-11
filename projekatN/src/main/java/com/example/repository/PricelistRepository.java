package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Building;
import com.example.model.Pricelist;

public interface PricelistRepository extends JpaRepository<Pricelist, Long>  {

}
