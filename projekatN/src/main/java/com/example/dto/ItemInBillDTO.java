package com.example.dto;

import com.example.model.ItemInBill;
import com.example.model.ItemInPrincelist;

public class ItemInBillDTO {
	private Long id;

	private String nameOfItem;
	private Double price;
	private int piece;

	public ItemInBillDTO() {

	}

	public ItemInBillDTO(ItemInBill itemInBill) {
		this(itemInBill.getId(), itemInBill.getNameOfItem(), itemInBill.getPrice(), itemInBill.getPiece());
	}

	public ItemInBillDTO(Long id, String nameOfItem, Double price, int piece) {
		super();
		this.id = id;
		this.nameOfItem = nameOfItem;
		this.price = price;
		this.piece=piece;
		
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

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
	

	public int getPiece() {
		return piece;
	}

	public void setPiece(int piece) {
		this.piece = piece;
	}

	public static ItemInBill getItemInBill(ItemInBillDTO itemInBillDTO) {
		return new ItemInBill(itemInBillDTO.getId(), itemInBillDTO.getNameOfItem(),
				itemInBillDTO.getPrice(), itemInBillDTO.getPiece());
	}
}
