package com.example.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.model.ItemInPrincelist;
import com.example.model.Pricelist;

public class PricelistDTO {
	
	private Long id;
	private Date dateUpdate;
	private String name;

	private Set<ItemInPricelistDTO> items = new HashSet<>();
	private UserDTO company;
	private GlitchTypeDTO type;
	

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

	public UserDTO getCompany() {
		return company;
	}

	public void setCompany(UserDTO company) {
		this.company = company;
	}

	public GlitchTypeDTO getType() {
		return type;
	}

	public void setType(GlitchTypeDTO type) {
		this.type = type;
	}

	public static Pricelist getPricelist(PricelistDTO pricelistDTO) {
		Pricelist p = new Pricelist(pricelistDTO.getId(), pricelistDTO.getDateUpdate());
		if (pricelistDTO.getCompany()!=null)
			p.setCompany(UserDTO.getUser(pricelistDTO.getCompany()));
		if (pricelistDTO.getType()!=null)
			p.setType(GlitchTypeDTO.getGlitchType(pricelistDTO.getType()));
		Set<ItemInPrincelist> items = new HashSet<>();
		for (ItemInPricelistDTO itemDTO : pricelistDTO.getItems()) {
			items.add(ItemInPricelistDTO.getItemInPricelist(itemDTO));
		}
		p.setItems(items);
		return p;
	}
}
