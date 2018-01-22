package com.example.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemInBill {
	@Id
	@GeneratedValue
	private Long id;
	
	private String nameOfItem;	
	private Double price;
	private int piece;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Bill bill;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameOfItem() {
		return nameOfItem;
	}

	public void setNameOfItem(String nameOfItem) {
		this.nameOfItem = nameOfItem;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getPiece() {
		return piece;
	}

	public void setPiece(int piece) {
		this.piece = piece;
	}

	public ItemInBill(Long id, String nameOfItem, Double price, int piece) {
		super();
		this.id = id;
		this.nameOfItem = nameOfItem;
		this.price = price;
		this.piece = piece;
	}

	public ItemInBill() {
		super();

	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	
	

}
