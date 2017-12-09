package com.example.dto;

import com.example.model.Apartment;

public class ApartmentDTO {

	private Long id;
	private Integer number;
	private String description;
	private UserDTO owner;

	public ApartmentDTO() {
	}

	public ApartmentDTO(Apartment apartment) {
		this(apartment.getId(), apartment.getNumber(), apartment.getDescription());

		if (apartment.getOwner() != null) {
			this.owner = new UserDTO(apartment.getOwner());
		}
	}

	public ApartmentDTO(Integer number, String description) {
		super();
		this.number = number;
		this.description = description;
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

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public static Apartment getApartment(ApartmentDTO apartmentDTO) {
		Apartment apartment = new Apartment(apartmentDTO.getId(), apartmentDTO.getNumber(), apartmentDTO.getDescription());
		if (apartmentDTO.getOwner() != null){
			apartment.setOwner(UserDTO.getUser(apartmentDTO.getOwner()));
		}
		return apartment;
	}
}
