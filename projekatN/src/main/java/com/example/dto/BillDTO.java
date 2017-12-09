package com.example.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.model.Bill;
import com.example.model.ItemInPrincelist;

public class BillDTO {

		
	private Long id;
	
	private Double price;
	private Date date;	
	private boolean approved;
	private Set<ItemInPricelistDTO> items = new HashSet<>();
	
	
	public BillDTO() {
	}
	
	public BillDTO(Bill bill) {
		this(bill.getId(), bill.getPrice(), bill.getDate(), bill.isApproved());
		for (ItemInPrincelist item : bill.getItems()) {
			items.add(new ItemInPricelistDTO(item));
		}		
		
	}
	
	public BillDTO(Long id, Double price, Set<ItemInPricelistDTO> items) {
		super();
		this.id = id;
		this.date = new Date();
		this.approved = false;
		this.items=items;
	}
	
	public BillDTO(Long id, Double price, Date date, boolean approved) {
		super();
		this.id = id;
		this.date = date;
		this.approved = approved;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	
	public Set<ItemInPricelistDTO> getItems() {
		return items;
	}

	public void setItems(Set<ItemInPricelistDTO> items) {
		this.items = items;
	}

	public static Bill getBill(BillDTO billDTO) {
		Set<ItemInPrincelist> new_items = new HashSet<ItemInPrincelist>();
		for (ItemInPricelistDTO item_In_Princelist : billDTO.getItems()) {
			new_items.add(item_In_Princelist.getItemInPricelist(item_In_Princelist));
		}
		return new Bill(billDTO.getId(), billDTO.getPrice(), billDTO.getDate(), billDTO.isApproved(), new_items );
	}

}
