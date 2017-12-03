package com.example.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Item_In_Princelist {
	@Id
	@GeneratedValue
	private Long id;
	
	private String nameOfType;	
	private Double price;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Pricelist pricelist;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Bill bill;


	public Item_In_Princelist(){
		
	}
	
	public Item_In_Princelist(Long id, String nameOfType, Double price) {
		super();
		this.id = id;
		this.nameOfType = nameOfType;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameOfType() {
		return nameOfType;
	}

	public void setNameOfType(String nameOfType) {
		this.nameOfType = nameOfType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Item_In_Princelist [id=" + id + ", nameOfType=" + nameOfType + ", price=" + price + "]";
	}
	
	

}
