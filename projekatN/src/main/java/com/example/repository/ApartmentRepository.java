package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
	@Query("SELECT a FROM Apartment a WHERE a.building.id = ?1 ")
	public Page<Apartment> findApartments(Long idBuilding, Pageable page);

	@Query("SELECT ap FROM Apartment ap, Building b WHERE b.id = ap.building.id AND "
			+ "b.address.street = ?1 AND b.address.number = ?2 AND b.address.city = ?3 AND ap.number=?4")
	Apartment findByAddress(String street, String number, String city, int numberApartment);

}
