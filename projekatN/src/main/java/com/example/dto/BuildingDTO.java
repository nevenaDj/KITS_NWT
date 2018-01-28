
package com.example.dto;

import com.example.model.Building;

public class BuildingDTO {

	private Long id;
	private AddressDTO address;
	private UserDTO president;

	public BuildingDTO() {
	}

	public BuildingDTO(Building building) {

		this.id = building.getId();
		if (building.getAddress() != null) {
			this.address = new AddressDTO(building.getAddress());
		}
		if (building.getPresident() != null) {
			this.president = new UserDTO(building.getPresident());
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

	public UserDTO getPresident() {
		return president;
	}

	public void setPresident(UserDTO president) {
		this.president = president;
	}

	public static Building getBuilding(BuildingDTO buildingDTO) {
		Building building = new Building();
		building.setId(buildingDTO.getId());

		if (buildingDTO.getAddress() != null) {
			building.setAddress(AddressDTO.getAddress(buildingDTO.getAddress()));
		}

		if (buildingDTO.getPresident() != null && buildingDTO.getPresident().getId() != null) {
			building.setPresident(UserDTO.getUser(buildingDTO.getPresident()));
		} else {
			building.setPresident(null);
		}

		return building;
	}

}
