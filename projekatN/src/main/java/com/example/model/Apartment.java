package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Apartment {
	@Id
	@GeneratedValue
	private Long id;

	private Integer number;
	private String description;

	public Apartment() {
	}

	public Apartment(Long id, Integer number, String description) {
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

}
