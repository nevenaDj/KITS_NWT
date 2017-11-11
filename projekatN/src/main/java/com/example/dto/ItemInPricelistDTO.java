package com.example.dto;

import com.example.model.Item_In_Princelist;
public class ItemInPricelistDTO {
	private Long id;
	
	private String nameOfType;	
	private Double price;
	
	public ItemInPricelistDTO() {

	}

	public ItemInPricelistDTO(Item_In_Princelist itemInPricelist) {
		this(itemInPricelist.getId(), itemInPricelist.getNameOfType(), itemInPricelist.getPrice());
	}

	public ItemInPricelistDTO(Long id, String nameOfType, Double price) {
		super();
		this.id = id;
		this.nameOfType = nameOfType;
		this.price= price;
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

	public static Item_In_Princelist getItemInPricelist(ItemInPricelistDTO itemInPricelistDTO) {
		return new Item_In_Princelist(itemInPricelistDTO.getId(), itemInPricelistDTO.getNameOfType(), itemInPricelistDTO.getPrice());
	}
}
