package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Building;
import com.example.model.Item_In_Princelist;

public interface ItemInPricelistRepository extends JpaRepository<Item_In_Princelist, Long> {

}
