package com.example.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Bill {

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Glitch glitch;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User company;

	@OneToMany(mappedBy = "bill", fetch = FetchType.EAGER)
	private Set<Item_In_Princelist> items = new HashSet<Item_In_Princelist>();
	
	private Double price;
	
	private Date date;
	
	private boolean approved;


	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Glitch getGlitch() {
		return glitch;
	}

	public void setGlitch(Glitch glitch) {
		this.glitch = glitch;
	}

	public User getCompany() {
		return company;
	}

	public void setCompany(User company) {
		this.company = company;
	}

	public Set<Item_In_Princelist> getItems() {
		return items;
	}

	public void setItems(Set<Item_In_Princelist> items) {
		this.items = items;
	}

	
	public Bill(Long id, Double price, Date date,
			boolean approved, Set<Item_In_Princelist> items) {
		super();
		this.id = id;
		this.price = price;
		this.date = date;
		this.approved = approved;
		this.items= items;
	}

	public Bill() {
		super();
		
	}
	
}
