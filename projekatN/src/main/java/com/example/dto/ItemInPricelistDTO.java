package com.example.dto;

import com.example.model.ItemInPrincelist;

public class ItemInPricelistDTO {
	private Long id;

	private String nameOfType;
	private Double price;

	public ItemInPricelistDTO() {

	}

	public ItemInPricelistDTO(ItemInPrincelist itemInPricelist) {
		this(itemInPricelist.getId(), itemInPricelist.getNameOfType(), itemInPricelist.getPrice());
	}

	public ItemInPricelistDTO(Long id, String nameOfType, Double price) {
		super();
		this.id = id;
		this.nameOfType = nameOfType;
		this.price = price;
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

	public String getNameOfType() {
		return nameOfType;
	}

	public void setNameOfType(String nameOfType) {
		this.nameOfType = nameOfType;
	}

	public static ItemInPrincelist getItemInPricelist(ItemInPricelistDTO itemInPricelistDTO) {
		return new ItemInPrincelist(itemInPricelistDTO.getId(), itemInPricelistDTO.getNameOfType(),
				itemInPricelistDTO.getPrice());
	}
}
