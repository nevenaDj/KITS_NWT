package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Building;

public interface BuildingRepository extends JpaRepository<Building, Long> {

	@Query("SELECT b FROM Building b, Address a WHERE b.address.id = a.id AND a.street = ?1 AND a.number = ?2 AND a.city = ?3")
	Building findByAddress(String street, String number, String city);

	@Query("SELECT b FROM Building b WHERE b.president.id=?1")
	List<Building> findAllByPresident(Long id);

	@Query("SELECT b FROM Building b WHERE b.president.id=?1")
	public List<Building> getBuildingsOfPresident(Long idPresident);

	@Query("SELECT a.building FROM Apartment a WHERE a.owner.id=?1")
	public List<Building> getBuildingsOfOwner(Long id);
	
	@Query("SELECT ua.apartment.building FROM UserAparment ua WHERE ua.tenant.id=?1")
	public List<Building> getBuildingsOfTenane(Long id);

}
