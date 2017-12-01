package com.example.dto;

import com.example.model.Building;

public class BuildingDTO {

	private Long id;
	private AddressDTO address;

	public BuildingDTO() {
	}

	public BuildingDTO(Building building) {

		this.id = building.getId();
		if (building.getAddress() != null) {
			this.address = new AddressDTO(building.getAddress());
		}
	}

	public BuildingDTO(AddressDTO address) {
		super();
		this.address = address;
	}

	public BuildingDTO(Long id, AddressDTO address) {
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

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public static Building getBuilding(BuildingDTO buildingDTO) {
		if (buildingDTO.getAddress() != null) {
			return new Building(buildingDTO.getId(), AddressDTO.getAddress(buildingDTO.getAddress()));
		} else {
			return new Building(buildingDTO.getId(), null);

		}
	}

}
