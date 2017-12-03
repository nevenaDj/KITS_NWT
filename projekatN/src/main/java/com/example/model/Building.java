package com.example.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Building {
	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Address address;

	@OneToOne(fetch = FetchType.EAGER)
	private User president;

	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
	private Set<Apartment> apartments = new HashSet<Apartment>();

	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
	private Set<Meeting> meetings = new HashSet<Meeting>();
	
	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
	private Set<Notification> notifications = new HashSet<Notification>();

	public Building() {
	}

	public Building(Long id, Address address) {
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
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

	public User getPresident() {
		return president;
	}

	public void setPresident(User president) {
		this.president = president;
	}

	public Set<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	
}
