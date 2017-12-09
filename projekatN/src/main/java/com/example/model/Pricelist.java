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
public class Pricelist {
	@Id
	@GeneratedValue
	private Long id;
	
	private Date dateUpdate;
	
	@OneToMany(mappedBy = "pricelist", fetch = FetchType.LAZY)
	private Set<ItemInPrincelist> items = new HashSet<>();
	
	@OneToOne(fetch = FetchType.EAGER)
	private User company;
	
	@OneToOne(fetch = FetchType.EAGER)
	private GlitchType type;


	
	public Pricelist() {
		
	}

	public Pricelist(Long id, Date dateUpdate ) {
		super();
		this.id = id;
		this.dateUpdate = dateUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public Set<ItemInPrincelist> getItems() {
		return items;
	}

	public void setItems(Set<ItemInPrincelist> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Pricelist [id=" + id + ", dateUpdate=" + dateUpdate + "]";
	}

	public User getCompany() {
		return company;
	}

	public void setCompany(User company) {
		this.company = company;
	}

	public GlitchType getType() {
		return type;
	}

	public void setType(GlitchType type) {
		this.type = type;
	}

}
