package com.example.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Building {
	@Id
	@GeneratedValue
	private Long id;

	private String address;

	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
	private Set<Apartment> apartments = new HashSet<Apartment>();

	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
	private Set<Meeting> meetings = new HashSet<Meeting>();

	public Building() {
	}

	public Building(Long id, String address) {
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

	public Set<Apartment> getApartments() {
		return apartments;
	}

	public void setApartments(Set<Apartment> apartments) {
		this.apartments = apartments;
	}

	public Set<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(Set<Meeting> meetings) {
		this.meetings = meetings;
	}

}
