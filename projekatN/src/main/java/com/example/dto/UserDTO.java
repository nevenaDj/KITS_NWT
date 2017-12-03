package com.example.dto;

import com.example.model.User;

public class UserDTO {
	private Long id;
	private String username;

	private String email;
	private AddressDTO address;
	private String phoneNo;

	public UserDTO() {

	}

	public UserDTO(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.phoneNo = user.getPhoneNo();

		if (user.getAddress() != null) {
			this.address = new AddressDTO(user.getAddress());
		}
	}

	public UserDTO(String username, String email, AddressDTO address, String phoneNo) {
		super();
		this.username = username;
		this.email = email;
		this.address = address;
		this.phoneNo = phoneNo;
	}

	public UserDTO(Long id, String username, String email, AddressDTO address, String phoneNo) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.address = address;
		this.phoneNo = phoneNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public static User getUser(UserDTO userDTO) {

		if (userDTO.getAddress() != null) {
			return new User(userDTO.getId(), userDTO.getUsername(), userDTO.getEmail(),
					AddressDTO.getAddress(userDTO.getAddress()), userDTO.getPhoneNo());
		} else {
			return new User(userDTO.getId(), userDTO.getUsername(), userDTO.getEmail(), null, userDTO.getPhoneNo());
		}

	}

}
