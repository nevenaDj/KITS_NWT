package com.example.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserAparment {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	private User tenant;

	@ManyToOne(fetch = FetchType.EAGER)
	private Apartment apartment;

	public UserAparment() {

	}

	public UserAparment(User tenant, Apartment aparment) {
		super();
		this.tenant = tenant;
		this.apartment = aparment;
	}

	public UserAparment(Long id, User tenant, Apartment aparment) {
		super();
		this.id = id;
		this.tenant = tenant;
		this.apartment = aparment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getTenant() {
		return tenant;
	}

	public void setTenant(User tenant) {
		this.tenant = tenant;
	}

	public Apartment getAparment() {
		return apartment;
	}

	public void setAparment(Apartment aparment) {
		this.apartment = aparment;
	}

}
