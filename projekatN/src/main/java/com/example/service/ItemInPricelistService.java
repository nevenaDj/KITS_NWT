package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.ItemInPrincelist;
import com.example.repository.ItemInPricelistRepository;

@Service
public class ItemInPricelistService {

	@Autowired
	ItemInPricelistRepository itemInPricelistRepository;

	public ItemInPrincelist findOne(Long id) {
		return itemInPricelistRepository.findOne(id);
	}

	public Page<ItemInPrincelist> findAll(Pageable page) {
		return itemInPricelistRepository.findAll(page);
	}

	public ItemInPrincelist save(ItemInPrincelist itemInPrincelist) {
		return itemInPricelistRepository.save(itemInPrincelist);
	}

	public void remove(Long id) {
		itemInPricelistRepository.delete(id);
	}

}
