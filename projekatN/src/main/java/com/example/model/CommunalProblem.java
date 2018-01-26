package com.example.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CommunalProblem {
	@Id
	@GeneratedValue
	private Long id;

	private String description;

	@ManyToOne(fetch = FetchType.EAGER)
	private Building building;

	@OneToOne(fetch = FetchType.EAGER)
	private GlitchType type;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfRepair;

	@ManyToOne(fetch = FetchType.EAGER)
	private User company;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Apartment> apartments = new HashSet<>();
	
	@OneToOne(fetch = FetchType.EAGER)
	private AgendaItem item;
	

	public CommunalProblem() {

	}

	public CommunalProblem(Long id, String description, Date dateOfRepair) {
		super();
		this.id = id;
		this.description = description;
		this.dateOfRepair = dateOfRepair;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public GlitchType getType() {
		return type;
	}

	public void setType(GlitchType type) {
		this.type = type;
	}

	public Date getDateOfRepair() {
		return dateOfRepair;
	}

	public void setDateOfRepair(Date dateOfRepair) {
		this.dateOfRepair = dateOfRepair;
	}

	public User getCompany() {
		return company;
	}

	public void setCompany(User company) {
		this.company = company;
	}

	public Set<Apartment> getApartments() {
		return apartments;
	}

	public void setApartments(Set<Apartment> apartments) {
		this.apartments = apartments;
	}

	public void addApartment(Apartment apartment) {
		this.apartments.add(apartment);
	}

	public AgendaItem getItem() {
		return item;
	}

	public void setItem(AgendaItem item) {
		this.item = item;
	}

	
}
