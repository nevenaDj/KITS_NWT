package com.example.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.model.Pricelist;
import com.example.model.Survey;
import com.example.model.SurveyType;

public class PricelistDTO {
	
	private Long id;
	private Date dateUpdate;

	private Set<ItemInPricelistDTO> items = new HashSet<ItemInPricelistDTO>();

	public PricelistDTO() {

	}

	public PricelistDTO(Pricelist pricelist) {
		this(pricelist.getId(), pricelist.getDateUpdate());
	}

	public PricelistDTO(Long id, Date date) {
		super();
		this.id = id;
		this.dateUpdate = date;
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

	public Set<ItemInPricelistDTO> getItems() {
		return items;
	}

	public void setItems(Set<ItemInPricelistDTO> items) {
		this.items = items;
	}

	public static Pricelist getPricelist(PricelistDTO pricelistDTO) {
		return new Pricelist(pricelistDTO.getId(), pricelistDTO.getDateUpdate());
	}
}
