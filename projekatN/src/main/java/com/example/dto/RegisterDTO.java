package com.example.dto;

import com.example.model.User;

public class RegisterDTO {
	private String username;

	private String password;
	private String password2;
	private String email;
	private AddressDTO address;
	private String phoneNo;
	private Long apartmentId;

	public RegisterDTO() {

	}

	public RegisterDTO(String username, String password, String password2, String email, AddressDTO address,
			String phoneNo) {
		super();
		this.username = username;
		this.password = password;
		this.password2 = password2;
		this.email = email;
		this.address = address;
		this.phoneNo = phoneNo;
	}

	public RegisterDTO(String username, String password, String password2, String email, AddressDTO address,
			String phoneNo, Long apartmentId) {
		super();
		this.username = username;
		this.password = password;
		this.password2 = password2;
		this.email = email;
		this.address = address;
		this.phoneNo = phoneNo;
		this.apartmentId = apartmentId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Long getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(Long apartmentId) {
		this.apartmentId = apartmentId;
	}

	public static User getTenant(RegisterDTO registerDTO) {
		if (registerDTO.getAddress() != null) {
			return new User(registerDTO.getUsername(), registerDTO.getPassword(), registerDTO.getEmail(),
					AddressDTO.getAddress(registerDTO.getAddress()), registerDTO.getPhoneNo());
		} else {
			return new User(registerDTO.getUsername(), registerDTO.getPassword(), registerDTO.getEmail(), null,
					registerDTO.getPhoneNo());

		}
	}

}
