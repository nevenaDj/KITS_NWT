package com.example.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.model.Bill;
import com.example.model.ItemInBill;
import com.example.model.ItemInPrincelist;

public class BillDTO {

	private Long id;

	private Double price;
	private Date date;
	private boolean approved;
	private Set<ItemInBillDTO> items = new HashSet<>();

	public BillDTO() {
	}

	public BillDTO(Bill bill) {
		id=bill.getId();
		price= bill.getPrice();
		date=bill.getDate();
		approved=bill.isApproved();
		for (ItemInBill item : bill.getItems()) {
			items.add(new ItemInBillDTO(item));
		}

	}

	public BillDTO(Long id, Double price, Set<ItemInBillDTO> items) {
		super();
		this.id = id;
		this.date = new Date();
		this.approved = false;
		this.items = items;
	}

	public BillDTO(Long id, Double price, Date date, boolean approved) {
		super();
		this.id = id;
		this.date = date;
		this.approved = approved;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Set<ItemInBillDTO> getItems() {
		return items;
	}

	public void setItems(Set<ItemInBillDTO> items) {
		this.items = items;
	}

	public static Bill getBill(BillDTO billDTO) {
		Set<ItemInBill> newItems = new HashSet<>();
		for (ItemInBillDTO itemInBillDTO : billDTO.getItems()) {
			newItems.add(ItemInBillDTO.getItemInBill(itemInBillDTO));
		}
		return new Bill(billDTO.getId(), billDTO.getPrice(), billDTO.getDate(), billDTO.isApproved(), newItems);
	}

}
