package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Item_In_Princelist;
import com.example.model.Pricelist;
import com.example.repository.ItemInPricelistRepository;
import com.example.repository.PricelistRepository;

@Service
public class ItemInPricelistService {

	@Autowired
	ItemInPricelistRepository itemInPricelistRepository;

	public Item_In_Princelist findOne(Long id) {
		return itemInPricelistRepository.findOne(id);
	}

	public Page<Item_In_Princelist> findAll(Pageable page) {
		return itemInPricelistRepository.findAll(page);
	}

	public Item_In_Princelist save(Item_In_Princelist item_In_Princelist) {
		return itemInPricelistRepository.save(item_In_Princelist);
	}

	public void remove(Long id) {
		itemInPricelistRepository.delete(id);
	}
}
