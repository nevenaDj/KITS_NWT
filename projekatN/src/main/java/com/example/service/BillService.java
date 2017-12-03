package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.dto.DateDTO;
import com.example.model.Bill;
import com.example.repository.BillRepository;

@Service
public class BillService {
	@Autowired
	BillRepository billRepository;

	public Bill findOne(Long id) {
		return billRepository.findOne(id);
	}
	
	public Bill findByGlitch(Long id) {
		return billRepository.findByGlitch(id);
	}
	
	public Bill findByDate(DateDTO date) {
		return billRepository.findByDate(date.getStart(), date.getEnd());
	}


	public Page<Bill> findAll(Pageable page) {
		return billRepository.findAll(page);
	}

	public Bill save(Bill bill) {
		return billRepository.save(bill);
	}

	public void remove(Long id) {
		billRepository.delete(id);
	}

}
