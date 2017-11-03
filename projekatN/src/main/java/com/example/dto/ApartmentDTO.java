package com.example.dto;

import com.example.model.Apartment;

public class ApartmentDTO {

	private Long id;
	private Integer number;
	private String description;

	public ApartmentDTO() {
	}

	public ApartmentDTO(Apartment apartment) {
		this(apartment.getId(), apartment.getNumber(), apartment.getDescription());
	}

	public ApartmentDTO(Long id, Integer number, String description) {
		super();
		this.id = id;
		this.number = number;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static Apartment getApartment(ApartmentDTO apartmentDTO) {
		return new Apartment(apartmentDTO.getId(), apartmentDTO.getNumber(), apartmentDTO.getDescription());
	}
}
