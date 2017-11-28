package com.example.dto;

import com.example.model.Building;

public class BuildingDTO {

	private Long id;
	private String address;

	public BuildingDTO() {
	}

	public BuildingDTO(Building building) {
		this(building.getId(), building.getAddress());
	}

	public BuildingDTO(String address) {
		super();
		this.address = address;
	}

	public BuildingDTO(Long id, String address) {
		super();
		this.id = id;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static Building getBuilding(BuildingDTO buildingDTO) {
		return new Building(buildingDTO.getId(), buildingDTO.getAddress());
	}

}
