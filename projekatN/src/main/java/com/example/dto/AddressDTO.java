package com.example.dto;

import com.example.model.Address;

public class AddressDTO {
	private Long id;

	private String street;
	private String number;
	private int zipCode;
	private String city;

	public AddressDTO() {

	}

	public AddressDTO(Address address) {
		this(address.getId(), address.getStreet(), address.getNumber(), address.getZipCode(), address.getCity());
	}

	public AddressDTO(Long id, String street, String number, int zipCode, String city) {
		super();
		this.id = id;
		this.street = street;
		this.number = number;
		this.zipCode = zipCode;
		this.city = city;
	}

	public AddressDTO(String street, String number, int zipCode, String city) {
		super();
		this.street = street;
		this.number = number;
		this.zipCode = zipCode;
		this.city = city;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public static Address getAddress(AddressDTO addressDTO) {
		return new Address(addressDTO.getId(), addressDTO.getStreet(), addressDTO.getNumber(), addressDTO.getZipCode(),
				addressDTO.getCity());

	}

}
